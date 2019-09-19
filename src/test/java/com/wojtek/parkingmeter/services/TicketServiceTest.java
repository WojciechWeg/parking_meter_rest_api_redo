package com.wojtek.parkingmeter.services;

import com.wojtek.parkingmeter.car.CarRepository;
import com.wojtek.parkingmeter.profit.ProfitRepository;
import com.wojtek.parkingmeter.ticket.TicketRepository;
import com.wojtek.parkingmeter.ticket.TicketService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

public class TicketServiceTest {

    TicketService ticketService;

    @Mock
    private  TicketRepository ticketRepository;

    @Mock
    private  CarRepository carRepository;

    @Mock
    private  ProfitRepository profitRepository;


    @Test
    public void startValidTicket(){};

    @Test
    public void startTicketWithInvalidTicketType(){}

    @Test
    public void startTicketWithInvalidNumberPlate(){}

    @Test
    public void stopValidTicket(){}

    @Test
    public void stopAlreadyStoppedTicket(){}

    @Test
    public void stopNonExistingTicket(){}

    @Test
    public void checkChargeOfExistingRunningTicket(){}

    @Test
    public void checkChargeOfStoppedTicket(){}

    @Test
    public void checkChargeOfNonExistingTicket(){}

}
