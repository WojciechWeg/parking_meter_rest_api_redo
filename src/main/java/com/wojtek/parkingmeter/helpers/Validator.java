package com.wojtek.parkingmeter.helpers;

import com.wojtek.parkingmeter.helpers.enums.TicketType;
import com.wojtek.parkingmeter.repositories.CarRepository;
import com.wojtek.parkingmeter.repositories.TicketRepository;
import com.wojtek.parkingmeter.services.TicketService;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;

@Component
public class Validator {


    private final CarRepository carRepository;
    private final TicketRepository ticketRepository;
    private final TicketService ticketService;

    public Validator(CarRepository carRepository, TicketRepository ticketRepository, TicketService ticketService) {
        this.carRepository = carRepository;
        this.ticketRepository = ticketRepository;
        this.ticketService = ticketService;
    }

    public  boolean validateNewTicket(String ticketType, String numberPlate) {

        if (ticketService.hasStarted(numberPlate))
            return false;

       return isTicketInputDataOK(ticketType,numberPlate);

    }

    private boolean isTicketInputDataOK(String ticketType, String numberPlate){
        return ( numberPlate.length() == 5 ) && TicketType.DISABLED.toString().equalsIgnoreCase(ticketType) ||
                TicketType.REGULAR.toString().equalsIgnoreCase(ticketType);
    }

    public String validateStopTicket(String idString){

        try {
            Long.parseLong(idString);
        }
        catch (NumberFormatException e){
            return "TICKET DOES NOT EXIST";
        }

        Long id = Long.parseLong(idString);

        if (!checkIfAlreadyStarted(id))
            return "TICKET ALREADY STOPPED";
        if (!checkIfExists(idString))
            return "TICKET DOES NOT EXIST";

        try {
            ticketService.stopTicket(id);
            return "TICKET STOPPED";
        } catch (NoSuchElementException e) {
            return "TICKET DOES NOT EXIST";
        }
    }

    public boolean checkNumberPlate(String numberPlate){
        return (numberPlate.length() == 5);
    }

    public  boolean checkIfAlreadyStarted(Long id) {

            return carRepository.existsById(id);

    }

    public  boolean checkIfExists(String idString) {

        try {
            Long.parseLong(idString);
        }
        catch (NumberFormatException e){
            return false;
        }

        Long id = Long.parseLong(idString);

        return ticketRepository.existsById(id);
    }
}
