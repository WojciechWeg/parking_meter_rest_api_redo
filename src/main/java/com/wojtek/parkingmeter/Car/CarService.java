package com.wojtek.parkingmeter.Car;

import com.wojtek.parkingmeter.helpers.Validator;
import org.springframework.stereotype.Service;

@Service
public class CarService {

    private final Validator validator;

    public CarService( Validator validator) {
        this.validator = validator;
    }


    public boolean hasStarted(String numberPlate) {

        validator.checkNumberPlate(numberPlate);

        return validator.hasStarted(numberPlate);

    }
}
