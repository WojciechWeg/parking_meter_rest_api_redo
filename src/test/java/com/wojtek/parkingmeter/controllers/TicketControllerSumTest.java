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
public class TicketControllerSumTest {

    @Autowired
    private MockMvc mockMvc;


    @Test
    public void shouldReturn34() throws Exception {

        this.mockMvc.perform(get("/sum"))
                .andExpect(content().json("{\"sum\":34.18}"))
                .andExpect(status().isOk());
    }

}