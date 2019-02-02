package com.wojtek.parkingmeter.services;

import com.wojtek.parkingmeter.model.TicketEntity;
import com.wojtek.parkingmeter.model.DTO.TicketDTO;

import java.math.BigDecimal;


public interface TicketService {

    TicketDTO startTicket(String ticketType, String numberPlate);

    TicketEntity stopTicket(Long id);

    BigDecimal checkCharge(Long id);

    BigDecimal checkSum();

}
