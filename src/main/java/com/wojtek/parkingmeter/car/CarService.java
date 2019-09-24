package com.wojtek.parkingmeter.car;

import com.wojtek.parkingmeter.helpers.Validator;
import com.wojtek.parkingmeter.ticket.TicketRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CarService {

    private final Validator validator;
    private final CarRepository carRepository;
    private final TicketRepository ticketRepository;

    public CarService(Validator validator, CarRepository carRepository, TicketRepository ticketRepository) {
        this.validator = validator;
        this.carRepository = carRepository;
        this.ticketRepository = ticketRepository;
    }

    public boolean hasStarted(String numberPlate) {

        validator.checkNumberPlate(numberPlate);

        Optional<Integer> carID = carRepository.findIdByNrPlate(numberPlate);

        if(carID.isPresent()) {
            if(hasValidTicket(numberPlate)){
                return true;
            }
            else{
                return false;
            }
        }

        return false;
    }

    public boolean hasValidTicket(String numberPlate){
        return ticketRepository.findByCarNumberPlateAndCarIdIsNull(numberPlate).isPresent();
    }
}
