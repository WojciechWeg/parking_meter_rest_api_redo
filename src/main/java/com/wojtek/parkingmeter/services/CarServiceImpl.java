package com.wojtek.parkingmeter.services;

import com.wojtek.parkingmeter.repositories.CarRepository;
import org.springframework.stereotype.Service;

@Service
public class CarServiceImpl implements CarService{

    private final CarRepository carRepository;

    public CarServiceImpl(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    @Override
    public boolean hasStarted(String numberPlate) {

        Integer carID = carRepository.findIdByNrPlate(numberPlate);

        return  (carID != null);

    }
}
