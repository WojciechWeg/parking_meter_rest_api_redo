package com.wojtek.parkingmeter.services;

import com.wojtek.parkingmeter.repositories.CarRepository;
import org.springframework.stereotype.Service;

@Service
public class CarService {

    private final CarRepository carRepository;

    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }


    public boolean hasStarted(String numberPlate) {

        Integer carID = carRepository.findIdByNrPlate(numberPlate);

        return  (carID != null);

    }
}
