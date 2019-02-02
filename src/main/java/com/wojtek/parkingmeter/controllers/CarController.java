package com.wojtek.parkingmeter.controllers;

import com.wojtek.parkingmeter.helpers.Validator;
import com.wojtek.parkingmeter.services.CarService;
import com.wojtek.parkingmeter.services.TicketService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CarController {

    private final CarService carService;
    private final Validator validator;

    public CarController(CarService carService, Validator validator) {
        this.carService = carService;
        this.validator = validator;
    }

    @GetMapping("/cars/{numberPlate}/hasStarted")
    public ResponseEntity<Boolean> hasStarted(@PathVariable String numberPlate) {

        if(validator.checkNumberPlate(numberPlate))
            return ResponseEntity.ok(carService.hasStarted(numberPlate));

        return ResponseEntity.ok(false);
    }
}
