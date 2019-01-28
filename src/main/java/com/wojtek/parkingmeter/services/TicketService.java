package com.wojtek.parkingmeter.services;

import com.wojtek.parkingmeter.model.DTO.HasStarted;
import com.wojtek.parkingmeter.model.DTO.Sum;
import com.wojtek.parkingmeter.model.TicketEntity;
import com.wojtek.parkingmeter.model.DTO.TicketDTO;

public interface TicketService {

    TicketDTO startTicket(String ticket_type, String nr_plate);

    TicketEntity stopTicket(Long id);

    String checkCharge(Long id);

    Sum checkSum();

    HasStarted hasStarted(String nr_plate);

}
