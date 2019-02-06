package com.wojtek.parkingmeter.Car;

import com.wojtek.parkingmeter.helpers.LaunchStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CarController {

    private final CarService carService;

    public CarController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping("/cars/{numberPlate}/launch-status")
    public LaunchStatus getLaunchStatus(@PathVariable String numberPlate) {
        return new LaunchStatus(carService.hasStarted(numberPlate));
    }

}
