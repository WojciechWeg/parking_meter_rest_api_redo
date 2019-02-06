package com.wojtek.parkingmeter.helpers;

import com.wojtek.parkingmeter.Ticket.TicketType;
import com.wojtek.parkingmeter.Ticket.TicketEntity;
import com.wojtek.parkingmeter.Ticket.TicketRepository;
import com.wojtek.parkingmeter.Car.CarService;
import com.wojtek.parkingmeter.exceptions.*;
import org.springframework.stereotype.Component;


import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Optional;

@Component
public class Validator {

    private final TicketRepository ticketRepository;
    private final CarService carService;

    public Validator(TicketRepository ticketRepository, CarService carService) {
        this.ticketRepository = ticketRepository;
        this.carService = carService;
    }

    public void validateNewTicket(String ticketType, String numberPlate) {

        if (carService.hasStarted(numberPlate))
            throw new CarAlreadyStartedException();

        isTicketInputDataOK(ticketType,numberPlate);

    }

    private void isTicketInputDataOK(String ticketType, String numberPlate){
        if (!(( numberPlate.length() == 5 ) && TicketType.DISABLED.toString().equalsIgnoreCase(ticketType) ||
                TicketType.REGULAR.toString().equalsIgnoreCase(ticketType)))
            throw new TicketIncorrectInputDataException();
    }


    public void isIdLong(String idString){

        try {
            Long.parseLong(idString);
        }
        catch (NumberFormatException e){
            throw new InvalidIDNumberException("Invalid ID Number Exception  id : " + idString);
        }

    }

    public boolean checkNumberPlate(String numberPlate){
        return (numberPlate.length() == 5);
    }

    public boolean hasStopped(Long id) {

        Optional<TicketEntity> stopTicketOpt = ticketRepository.findById(id);

        TicketEntity stopTicketEntity;

        if(stopTicketOpt.isPresent())
            stopTicketEntity = stopTicketOpt.get();
        else
            throw new NoSuchElementException();

        try {
            LocalDateTime stopStamp = stopTicketEntity.getStampStop();
            System.out.println(stopStamp);
        }catch (NullPointerException e){
            return false;
        }

            return true;

    }

    private boolean checkIfExists(String idString) {

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
