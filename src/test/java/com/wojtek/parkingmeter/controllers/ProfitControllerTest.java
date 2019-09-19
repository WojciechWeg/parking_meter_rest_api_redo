package com.wojtek.parkingmeter.controllers;


import com.wojtek.parkingmeter.profit.ProfitController;
import com.wojtek.parkingmeter.profit.ProfitService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ProfitController.class)
public class ProfitControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProfitService profitService;

    @Test
    public void getProfitThatExist(){}

    @Test
    public void getProfitThatDoesNotExist(){}

}
