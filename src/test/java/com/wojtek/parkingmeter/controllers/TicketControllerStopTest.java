package com.wojtek.parkingmeter.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
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
public class TicketControllerStopTest {


    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldReturnTICKET_DOES_NOT_EXIST() throws Exception {

        this.mockMvc.perform(get("/stop/666"))
                .andExpect(status().is(400))
                .andExpect(content().string("TICKET DOES NOT EXIST"));
    }


    @Test
    public void shouldReturnTICKET_ALREADY_STOPPED() throws Exception {

        this.mockMvc.perform(get("/stop/4"))
                .andExpect(status().is(200))
                .andExpect(content().string("TICKET ALREADY STOPPED"));
    }


    @Test
    public void shouldReturnTICKET_STOPPED() throws Exception {

        this.mockMvc.perform(get("/start/DISABLED/w7333"))
                .andExpect(status().isOk());

        this.mockMvc.perform(get("/stop/9"))
                .andExpect(status().is(200))
                .andExpect(content().string("TICKET STOPPED"));

    }


}
