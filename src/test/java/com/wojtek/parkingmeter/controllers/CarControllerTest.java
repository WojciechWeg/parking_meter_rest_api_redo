package com.wojtek.parkingmeter.controllers;

import com.wojtek.parkingmeter.car.CarService;
import com.wojtek.parkingmeter.exceptions.InvalidNumberPlateException;
import com.wojtek.parkingmeter.helpers.LaunchStatus;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import com.wojtek.parkingmeter.car.CarController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CarController.class)
public class CarControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CarService carService;

    @Test
    public void getStatusOfExistingCar() throws Exception {

        when(carService.hasStarted(any())).thenReturn(true);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                                    .get("/cars/AS123/launch-status"))
                                    .andExpect(status().isOk())
                                    .andExpect(jsonPath("$.started", Matchers.is(true)))
                                    .andReturn();
    }

    @Test
    public void getStatusOfNonExistingCar() throws Exception {

        when(carService.hasStarted(any())).thenReturn(false);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .get("/cars/AS123/launch-status"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.started", Matchers.is(false)))
                .andReturn();

    }

    @Test
    public void checkInvalidNumberPlateException() throws Exception {

        when(carService.hasStarted(any())).thenThrow(new InvalidNumberPlateException("Invalid number plate"));

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .get("/cars/AS123d/launch-status"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", Matchers.is("Invalid number plate")))
                .andReturn();

    }

    @Test
    public void carOnaParkingNoTicket() throws Exception {

        when(carService.hasStarted(any())).thenReturn(false);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .get("/cars/00000/launch-status"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.started", Matchers.is(false)))
                .andReturn();

    }

    @Test
    public void carOnaParkingHaveTicket() throws Exception {

        when(carService.hasStarted(any())).thenReturn(true);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .get("/cars/00000/launch-status"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.started", Matchers.is(true)))
                .andReturn();

    }

}
