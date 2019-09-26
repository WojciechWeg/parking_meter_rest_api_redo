package com.wojtek.parkingmeter.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wojtek.parkingmeter.exceptions.InvalidNumberPlateException;
import com.wojtek.parkingmeter.exceptions.TicketDoesNotExistException;
import com.wojtek.parkingmeter.exceptions.TicketStoppedException;
import com.wojtek.parkingmeter.profit.ProfitController;
import com.wojtek.parkingmeter.ticket.*;
import org.hamcrest.Matchers;
import org.junit.experimental.results.ResultMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(TicketController.class)
public class TicketControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TicketService ticketService;

    TicketStartDTO ticketStartDTO;
    TicketStopDTO ticketStopDTO;
    TicketDTO  ticketDTO;

    @BeforeEach
    public void setUp(){

        ticketStartDTO = new TicketStartDTO();
        ticketStartDTO.setCarNumberPlate("WR123");
        ticketStartDTO.setTicketType(TicketType.REGULAR);

        ticketDTO = new TicketDTO();
        ticketDTO.setId(10L);
        ticketDTO.setCarNumberPlate("WR123");
        ticketDTO.setStampStart(LocalDateTime.now());
        ticketDTO.setTicketType(TicketType.REGULAR);

        ticketStopDTO = new TicketStopDTO();
        ticketStopDTO.setId(11L);
        ticketStopDTO.setCarNumberPlate("WE123");
        ticketStopDTO.setStampStart(LocalDateTime.now());
        ticketStopDTO.setStampStop(LocalDateTime.now());
        ticketStopDTO.setCharge(BigDecimal.valueOf(14));
        ticketStopDTO.setTicketType(TicketType.DISABLED);
    }

    @Test
    public void postCreateValidTicket() throws Exception {

        when(ticketService.startTicket(any(),any())).thenReturn(ticketDTO);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .post("/tickets")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(ticketStartDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ticketType",Matchers.is("REGULAR")))
                .andExpect(jsonPath("$.stampStart",Matchers.anything()))
                .andExpect(jsonPath("$.id",Matchers.is(10)))
                .andExpect(jsonPath("$.carNumberPlate",Matchers.is("WR123")))
                .andReturn();

    }

    @Test
    public void postCreateNotValidTicketInvalidNrPlate() throws Exception {

        ticketStartDTO.setCarNumberPlate("WT1234");

        when(ticketService.startTicket(any(),any())).thenThrow(new InvalidNumberPlateException("Invalid number plate"));

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .post("/tickets")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(ticketStartDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message",Matchers.is("Invalid number plate")))
                .andReturn();

    }


    @Test
    public void postCreateMissingNrPlateField() throws Exception {

        ticketStartDTO.setTicketType(null);

        when(ticketService.startTicket(any(),any())).thenReturn(null);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .post("/tickets")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(ticketStartDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").doesNotExist())
                .andReturn();

    }

    @Test
    public void postCreateMissingTicketTypeField() throws Exception {


        ticketStartDTO.setCarNumberPlate(null);

        when(ticketService.startTicket(any(),any())).thenReturn(null);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .post("/tickets")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(ticketStartDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").doesNotExist())
                .andReturn();

    }

    @Test
    public void putStopRunningTicket() throws Exception {


        when(ticketService.stopTicket(any())).thenReturn(ticketStopDTO);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .put("/tickets/11")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ticketType",Matchers.is("DISABLED")))
                .andExpect(jsonPath("$.stampStart",Matchers.anything()))
                .andExpect(jsonPath("$.stampStop",Matchers.anything()))
                .andExpect(jsonPath("$.id",Matchers.is(11)))
                .andExpect(jsonPath("$.carNumberPlate",Matchers.is("WE123")))
                .andExpect(jsonPath("$.charge",Matchers.is(14)))
                .andReturn();

    }


    @Test
    public void getChargeOfExistingTicket() throws Exception {

        when(ticketService.checkCharge(any())).thenReturn(ticketStopDTO.getCharge());

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .get("/tickets/11/charge"))
                .andExpect(status().isOk())
                .andReturn();

       String resultString = result.getResponse().getContentAsString();

       assertEquals(resultString,String.valueOf(14));

    }

    @Test
    public void getChargeOfNotExistingTicket() throws Exception {

        when(ticketService.checkCharge(any())).thenThrow(new TicketDoesNotExistException("Ticket with given ID does not exist: 114"));

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .get("/tickets/114/charge"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.timestamp",Matchers.anything()))
                .andExpect(jsonPath("$.message",Matchers.is("Ticket with given ID does not exist: 114")))
                .andReturn();

    }


    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
