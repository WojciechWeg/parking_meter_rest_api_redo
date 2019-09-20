package com.wojtek.parkingmeter.controllers;

import com.wojtek.parkingmeter.profit.ProfitController;
import com.wojtek.parkingmeter.profit.ProfitRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import java.math.BigDecimal;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ProfitController.class)
public class ProfitControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    ProfitRepository profitRepository;

    @Test
    public void getProfitThatExist() throws Exception {

        when(profitRepository.sumIncomeByDate(any())).thenReturn(BigDecimal.ONE);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .get("/profit")
                .param("date",  "2019-03-12"))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void getProfitThatDoesNotExist() throws Exception {

        when(profitRepository.sumIncomeByDate(any())).thenReturn(null);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .get("/profit")
                .param("date",  "2019-03-12"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").doesNotExist())
                .andReturn();
    }

}
