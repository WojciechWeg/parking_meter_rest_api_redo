package com.wojtek.parkingmeter.controllers;

import com.wojtek.parkingmeter.profit.ProfitController;
import com.wojtek.parkingmeter.ticket.TicketService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ProfitController.class)
public class TicketControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TicketService ticketService;

    @Test
    public void postCreateValidTicket(){}

    @Test
    public void postCreateNotValidTicketInvalidNrPlate(){}

    @Test
    public void postCreateNotValidTicketInvalidTicketType(){}

    @Test
    public void postCreateNotValidTicketNoNrPlate(){}

    @Test
    public void postCreateNotValidTicketNoTicketType(){}

    @Test
    public void putStopRunningTicket(){}

    @Test
    public void putStopStoppedTicket(){}

    @Test
    public void getChargeOfExistingTicket(){}

    @Test
    public void getChargeOfNotExistingTicket(){}

}
