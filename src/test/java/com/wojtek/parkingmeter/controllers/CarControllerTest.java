package com.wojtek.parkingmeter.controllers;

import com.wojtek.parkingmeter.car.CarService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import com.wojtek.parkingmeter.car.CarController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CarController.class)
public class CarControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CarService currencyService;


    @Test
    public void getStatusOfExistingCar(){}

    @Test
    public void getStatusOfNonExistingCar(){}

    @Test
    public void checkInvalidNumberPlateException(){}

}
