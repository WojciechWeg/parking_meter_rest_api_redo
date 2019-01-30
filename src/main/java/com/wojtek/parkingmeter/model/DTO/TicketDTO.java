package com.wojtek.parkingmeter.model.DTO;

import com.wojtek.parkingmeter.helpers.enums.TicketType;

import java.time.LocalDateTime;

public class TicketDTO {

    public TicketDTO() {
    }

    public TicketDTO(TicketType ticketType, double charge, LocalDateTime stampStart) {
        this.ticketType = ticketType;
        this.charge = charge;
        this.stampStart = stampStart;
    }

    private TicketType ticketType;

    private double charge;

    private LocalDateTime stampStart;

    private Long id;

    public TicketType getTicketType() {
        return ticketType;
    }

    public void setTicketType(TicketType ticketType) {
        this.ticketType = ticketType;
    }

    public double getCharge() {
        return charge;
    }

    public void setCharge(double charge) {
        this.charge = charge;
    }

    public LocalDateTime getStampStart() {
        return stampStart;
    }

    public void setStampStart(LocalDateTime stampStart) {
        this.stampStart = stampStart;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
