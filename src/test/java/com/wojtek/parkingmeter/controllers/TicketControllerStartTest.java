package com.wojtek.parkingmeter.controllers;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TicketControllerStartTest {


    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldReturn200Status() throws Exception {
        this.mockMvc.perform(get("/start/disabled/wn333"))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldReturn400Status() throws Exception {
        this.mockMvc.perform(get("/start/disabled/wn333"))
                .andExpect(status().isImUsed())
                .andExpect(content().json("{\"ticketType\":null,\"charge\":0.0,\"stampStart\":null,\"id\":null}"));
    }

    @Test
    public void shouldReturn400BadTicketType() throws Exception {
        this.mockMvc.perform(get("/start/ABCDE/wn733"))
                .andExpect(status().is(400))
                .andExpect(content().json("{\"ticketType\":null,\"charge\":0.0,\"stampStart\":null,\"id\":null}"));
    }

    @Test
    public void shouldReturn400BadNrPlate() throws Exception {
        this.mockMvc.perform(get("/start/regular/wn33344444"))
                .andExpect(status().is4xxClientError())
                .andExpect(content().json("{\"ticketType\":null,\"charge\":0.0,\"stampStart\":null,\"id\":null}"));
    }

}
