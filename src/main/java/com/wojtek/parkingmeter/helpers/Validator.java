package com.wojtek.parkingmeter.helpers;

import com.wojtek.parkingmeter.Car.CarRepository;
import com.wojtek.parkingmeter.Ticket.TicketRepository;
import com.wojtek.parkingmeter.exceptions.*;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class Validator {

    private final TicketRepository ticketRepository;
    private final CarRepository carRepository;

    public Validator(TicketRepository ticketRepository, CarRepository carRepository) {
        this.ticketRepository = ticketRepository;
        this.carRepository = carRepository;
    }

    public void validateNewTicket(String ticketType, String numberPlate) {

        if (hasStarted(numberPlate))
            throw new CarAlreadyStartedException("This car already have valid ticket.");

        isTicketInputDataOK(ticketType,numberPlate);

    }

    private void isTicketInputDataOK(String ticketType, String numberPlate){
        checkNumberPlate(numberPlate);

        // w tym miejscu chciałbym zwalidować ticket type ale nie wiem jak
    }


    public void isIdLong(String idString){

        try {
            Long.parseLong(idString);
        }
        catch (NumberFormatException e){
            throw new InvalidIDNumberException("Invalid ID Number Exception  id : " + idString);
        }

    }

    public void checkNumberPlate(String numberPlate){
        if (numberPlate.length() != 5)
            throw new InvalidNumberPlateException("Invalid number plate");
    }

    public boolean hasStarted(String numberPlate){


        Optional<Integer> carID = carRepository.findIdByNrPlate(numberPlate);


        if(carID.isPresent())
            return true;
        else
            return false;

    }
}
