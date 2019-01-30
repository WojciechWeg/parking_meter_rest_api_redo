package com.wojtek.parkingmeter.helpers;

import com.wojtek.parkingmeter.helpers.enums.TicketType;
import com.wojtek.parkingmeter.repositories.CarRepository;
import com.wojtek.parkingmeter.repositories.TicketRepository;
import org.springframework.stereotype.Component;

@Component
public class Validator {


    private final CarRepository carRepository;
    private final TicketRepository ticketRepository;

    public Validator(CarRepository carRepository, TicketRepository ticketRepository) {
        this.carRepository = carRepository;
        this.ticketRepository = ticketRepository;
    }

    public  boolean validateNewTicket(String ticketType, String numberPlate) {

        if (numberPlate.length() == 5) {
            if (TicketType.DISABLED.toString().equals(ticketType.toUpperCase()) ||
                    TicketType.REGULAR.toString().equals(ticketType.toUpperCase())) {
                return true;
            }

        }
        return false;
    }

    public  boolean checkIfAlreadyStarted(Long id) {

            return carRepository.existsById(id);

    }

    public  boolean checkIfExists(Long id) {

        return ticketRepository.existsById(id);
    }
}
