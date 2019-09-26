package com.wojtek.parkingmeter.services;

import com.wojtek.parkingmeter.car.CarEntity;
import com.wojtek.parkingmeter.car.CarRepository;
import com.wojtek.parkingmeter.exceptions.TicketDoesNotExistException;
import com.wojtek.parkingmeter.helpers.Validator;
import com.wojtek.parkingmeter.helpers.calcs.ChargeCalculator;
import com.wojtek.parkingmeter.profit.ProfitRepository;
import com.wojtek.parkingmeter.ticket.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TicketServiceTest {

    Validator validator;
    TicketService ticketService;

    TicketEntity ticketEntity;
    TicketStartDTO ticketStartDTO;
    TicketStopDTO ticketStopDTO;
    TicketDTO ticketDTO;
    CarEntity carEntity;

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

        ticketStartDTO = new TicketStartDTO();
        ticketStartDTO.setTicketType(TicketType.REGULAR);
        ticketStartDTO.setCarNumberPlate("12345");

        ticketStopDTO = new TicketStopDTO();
        ticketStopDTO.setId(1L);
        ticketStopDTO.setCarNumberPlate("12345");
        ticketStopDTO.setCharge(BigDecimal.valueOf(0));
        ticketStopDTO.setStampStart(LocalDateTime.now());
        ticketStopDTO.setStampStop(LocalDateTime.now());
        ticketStopDTO.setTicketType(TicketType.REGULAR);

        ticketDTO = new TicketDTO();
        ticketDTO.setCarNumberPlate("12345");
        ticketDTO.setTicketType(TicketType.REGULAR);
        ticketDTO.setId(1L);
        ticketDTO.setStampStart(LocalDateTime.now());


        ticketEntity = new TicketEntity();

        carEntity = new CarEntity("12345");
        carEntity.setTicket(ticketEntity);
        carEntity.setId(1L);

        ticketEntity.setCarNumberPlate("12345");
        ticketEntity.setTicketType(TicketType.REGULAR);
        ticketEntity.setStampStart(LocalDateTime.now());
        ticketEntity.setStampStop(LocalDateTime.now());
        ticketEntity.setId(1L);
        ticketEntity.setCharge(BigDecimal.valueOf(0));
        ticketEntity.setCarEntity(carEntity);
    }

    @Test
    public void startValidTicket(){

        when(carRepository.save(any())).thenReturn(carEntity);
        when(ticketRepository.save(any())).thenReturn(ticketEntity);
        when(ticketMapper.ticketToTicketDTO(any())).thenReturn(ticketDTO);

        TicketDTO  ticketDTOreturned = ticketService.startTicket(TicketType.REGULAR.toString(),"12345");

        assertEquals(ticketDTO.getCarNumberPlate(),ticketDTOreturned.getCarNumberPlate());
        assertEquals(ticketDTO.getTicketType(),ticketDTOreturned.getTicketType());
        assertEquals(ticketDTO.getId(),ticketDTOreturned.getId());
        assertEquals(ticketDTO.getStampStart(),ticketDTOreturned.getStampStart());
        assertEquals(ticketDTO.getId(),ticketDTOreturned.getId());

    };

    @Test
    public void stopValidTicket(){

        when(ticketRepository.findById(any())).thenReturn(Optional.of(ticketEntity));
        when(chargeCalculator.charge(any(),any())).thenReturn(BigDecimal.ZERO);
        when(ticketRepository.save(any())).thenReturn(ticketEntity);
        when(ticketMapper.ticketEntityToTicketPutDTO(any())).thenReturn(ticketStopDTO);

        TicketStopDTO ticketStopDTOReturned = ticketService.stopTicket("1");

        assertEquals(ticketStopDTOReturned.getId(), ticketStopDTO.getId());
        assertEquals(ticketStopDTOReturned.getCharge(), ticketStopDTO.getCharge());
        assertEquals(ticketStopDTOReturned.getCarNumberPlate(), ticketStopDTO.getCarNumberPlate());
        assertEquals(ticketStopDTOReturned.getStampStart(), ticketStopDTO.getStampStart());
        assertEquals(ticketStopDTOReturned.getStampStop(), ticketStopDTO.getStampStop());
        assertEquals(ticketStopDTOReturned.getTicketType(), ticketStopDTO.getTicketType());

    }


    @Test
    public void stopNonExistingTicket(){

        assertThrows(TicketDoesNotExistException.class, () -> ticketService.stopTicket("111"));

    }

    @Test
    public void checkChargeOfTicket(){

        when(ticketRepository.findById(any())).thenReturn(Optional.of(ticketEntity));
        when(chargeCalculator.charge(any(),any())).thenReturn(BigDecimal.ZERO);

        BigDecimal chargeReturned = ticketService.checkCharge("1");

        assertEquals(BigDecimal.valueOf(0),chargeReturned);
    }

}
