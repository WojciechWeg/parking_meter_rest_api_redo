package com.wojtek.parkingmeter.services;

import com.wojtek.parkingmeter.car.CarRepository;
import com.wojtek.parkingmeter.helpers.Validator;
import com.wojtek.parkingmeter.helpers.calcs.ChargeCalculator;
import com.wojtek.parkingmeter.profit.ProfitRepository;
import com.wojtek.parkingmeter.ticket.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TicketServiceTest {

    Validator validator;
    TicketService ticketService;
    TicketDTO ticketDTO;

    @Mock
    ChargeCalculator chargeCalculator;

    @Mock
    TicketMapper ticketMapper;

    @Mock
    private  TicketRepository ticketRepository;

    @Mock
    private  CarRepository carRepository;

    @Mock
    private  ProfitRepository profitRepository;

    @BeforeEach
    public void setUp(){
        validator = new Validator(ticketRepository,carRepository);
        ticketService = new TicketService(ticketRepository,carRepository,profitRepository,ticketMapper,chargeCalculator,validator);

        ticketDTO = new TicketDTO();
        ticketDTO.setCarNumberPlate("12345");
        ticketDTO.setTicketType(TicketType.REGULAR);
        ticketDTO.setId(1L);
        ticketDTO.setStampStart(LocalDateTime.now());
    }

    @Test
    public void startValidTicket(){

        when(ticketService.startTicket(any(),any())).thenReturn(ticketDTO);

        TicketDTO  ticketDTOreturned = ticketService.startTicket(TicketType.REGULAR.toString(),"12345");

        assertEquals(ticketDTO.getCarNumberPlate(),ticketDTOreturned.getCarNumberPlate());
        assertEquals(ticketDTO.getTicketType(),ticketDTOreturned.getTicketType());
        assertEquals(ticketDTO.getId(),ticketDTOreturned.getId());
        assertEquals(ticketDTO.getStampStart(),ticketDTOreturned.getStampStart());

    };

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
