package com.wojtek.parkingmeter.helpers;

import com.wojtek.parkingmeter.helpers.enums.TicketType;
import com.wojtek.parkingmeter.model.TicketEntity;
import com.wojtek.parkingmeter.repositories.CarRepository;
import com.wojtek.parkingmeter.repositories.TicketRepository;
import com.wojtek.parkingmeter.services.CarService;
import com.wojtek.parkingmeter.services.TicketService;
import org.springframework.stereotype.Component;
import sun.security.krb5.internal.Ticket;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Optional;

@Component
public class Validator {


    private final CarRepository carRepository;
    private final TicketRepository ticketRepository;
    private final TicketService ticketService;
    private final CarService carService;

    public Validator(CarRepository carRepository, TicketRepository ticketRepository, TicketService ticketService, CarService carService) {
        this.carRepository = carRepository;
        this.ticketRepository = ticketRepository;
        this.ticketService = ticketService;
        this.carService = carService;
    }

    public  boolean validateNewTicket(String ticketType, String numberPlate) {

        if (carService.hasStarted(numberPlate))
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
            return "INVALID ID";
        }

        Long id = Long.parseLong(idString);
        try {
            if (hasStopped(id))
                return "TICKET ALREADY STOPPED";
            if (!checkIfExists(idString))
                return "TICKET DOES NOT EXIST";

                ticketService.stopTicket(id);
                return "TICKET STOPPED";
        } catch (NoSuchElementException e) {
            return "TICKET DOES NOT EXIST";
        }
    }

    public boolean checkNumberPlate(String numberPlate){
        return (numberPlate.length() == 5);
    }

    public  boolean hasStopped(Long id) {

        Optional<TicketEntity> stopTicketOpt = ticketRepository.findById(id);

        TicketEntity stopTicketEntity;

        if(stopTicketOpt.isPresent())
            stopTicketEntity = stopTicketOpt.get();
        else
            throw new NoSuchElementException();


        if(stopTicketEntity.getStampStop().equals(LocalDateTime.of(0, 1, 1, 0, 0, 0, 0)))
            return false;
        else
            return true;

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
