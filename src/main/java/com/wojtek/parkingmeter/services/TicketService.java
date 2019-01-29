package com.wojtek.parkingmeter.services;

import com.wojtek.parkingmeter.model.TicketEntity;
import com.wojtek.parkingmeter.model.DTO.TicketDTO;

public interface TicketService {

    TicketDTO startTicket(String ticketType, String numberPlate);

    TicketEntity stopTicket(Long id);

    double checkCharge(Long id);

    double checkSum();

    boolean hasStarted(String numberPlate);

}
