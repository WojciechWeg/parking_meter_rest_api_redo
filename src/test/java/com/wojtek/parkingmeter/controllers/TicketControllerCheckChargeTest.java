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
public class TicketControllerCheckChargeTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldReturnOne() throws Exception {

        this.mockMvc.perform(get("/check_charge/1"))
                .andExpect(content().string("1.0"))
                .andExpect(status().isOk());
    }


    @Test
    public void shouldReturnThree() throws Exception {

        this.mockMvc.perform(get("/check_charge/2"))
                .andExpect(content().string("3.0"))
                .andExpect(status().isOk());
    }


    @Test
    public void shouldReturnTICKET_DOES_NOT_EXIST() throws Exception {

        this.mockMvc.perform(get("/check_charge/29999"))
                .andExpect(content().string("TICKET DOES NOT EXIST"))
                .andExpect(status().is(400));
    }

}
