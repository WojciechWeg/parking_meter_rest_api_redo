package com.wojtek.parkingmeter.Car;

import com.wojtek.parkingmeter.helpers.Validator;
import org.springframework.stereotype.Service;

@Service
public class CarService {

    private final CarRepository carRepository;
    private final Validator validator;

    public CarService(CarRepository carRepository, Validator validator) {
        this.carRepository = carRepository;
        this.validator = validator;
    }


    public boolean hasStarted(String numberPlate) {

        validator.checkNumberPlate(numberPlate);

        return validator.hasStarted(numberPlate);

    }
}
