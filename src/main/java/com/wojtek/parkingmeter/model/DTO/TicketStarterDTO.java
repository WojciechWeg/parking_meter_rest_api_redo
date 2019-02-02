package com.wojtek.parkingmeter.model.DTO;

import com.wojtek.parkingmeter.helpers.enums.TicketType;

public class TicketStarterDTO {


    private String carNumberPlate;

    private TicketType ticketType;


    public String getCarNumberPlate() {
        return carNumberPlate;
    }

    public void setCarNumberPlate(String carNumberPlate) {
        this.carNumberPlate = carNumberPlate;
    }

    public TicketType getTicketType() {
        return ticketType;
    }

    public void setTicketType(TicketType ticketType) {
        this.ticketType = ticketType;
    }
}
