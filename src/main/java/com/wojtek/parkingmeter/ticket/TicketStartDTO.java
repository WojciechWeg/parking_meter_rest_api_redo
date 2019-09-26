package com.wojtek.parkingmeter.ticket;

import javax.validation.constraints.NotNull;

public class TicketStartDTO {

    @NotNull
    private String carNumberPlate;

    @NotNull
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
