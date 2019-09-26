package com.wojtek.parkingmeter.services;

import com.wojtek.parkingmeter.car.CarRepository;
import com.wojtek.parkingmeter.car.CarService;
import com.wojtek.parkingmeter.exceptions.InvalidNumberPlateException;
import com.wojtek.parkingmeter.helpers.Validator;
import com.wojtek.parkingmeter.ticket.TicketRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CarServiceTest {

    Validator validator;
    CarService carService;
    @Mock
    CarRepository carRepository;

    @Mock
    TicketRepository ticketRepository;

    @BeforeEach
    public void setUp(){

        validator = new Validator(ticketRepository,carRepository);
        carService = new CarService(validator,carRepository,ticketRepository);

    }

    @Test
    public void checkCarWithValidTicketIfHasStarted(){

        when(carRepository.findIdByNrPlate(any())).thenReturn(Optional.of(1));
        when(ticketRepository.findByCarNumberPlateAndCarIdIsNull(any())).thenReturn(Optional.of(1L));

        boolean returnedBoolean = carService.hasStarted("12345");

        assertTrue(returnedBoolean);

    }

    @Test
    public void checkCarWithNoValidTicketIfHasStarted(){

        when(carRepository.findIdByNrPlate(any())).thenReturn(Optional.of(1));
        when(ticketRepository.findByCarNumberPlateAndCarIdIsNull(any())).thenReturn(Optional.empty());

        boolean returnedBoolean = carService.hasStarted("12345");

        assertFalse(returnedBoolean);


    }

    @Test
    public void checkCarWithInvalidNumberPlate(){

        assertThrows(InvalidNumberPlateException.class, () -> carService.hasStarted("123456"));

    }


}
