package com.wojtek.parkingmeter.controllers;

import com.wojtek.parkingmeter.profit.ProfitController;
import com.wojtek.parkingmeter.profit.ProfitRepository;
import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import java.math.BigDecimal;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@AutoConfigureRestDocs()
@ExtendWith(SpringExtension.class)
@WebMvcTest(ProfitController.class)
public class ProfitControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    ProfitRepository profitRepository;

    @Rule
    public JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation();

    @Test
    public void getProfitThatExist() throws Exception {

        when(profitRepository.sumIncomeByDate(any())).thenReturn(BigDecimal.ONE);

        MvcResult result = mockMvc.perform(RestDocumentationRequestBuilders
                .get("/profit")
                .param("date",  "2019-03-12"))
                .andExpect(status().isOk())
                .andDo(document("profitcontroller",
                        requestParameters(
                                parameterWithName("date").description("Date of requested profits")
                        )))
                .andReturn();
    }

    @Test
    public void getProfitThatDoesNotExist() throws Exception {

        when(profitRepository.sumIncomeByDate(any())).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders
                .get("/profit")
                .param("date",  "2019-03-12"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").doesNotExist())
                .andReturn();
    }

}
