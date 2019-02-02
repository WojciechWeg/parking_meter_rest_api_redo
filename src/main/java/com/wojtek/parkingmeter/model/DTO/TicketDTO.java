package com.wojtek.parkingmeter.model.DTO;

import com.wojtek.parkingmeter.helpers.enums.TicketType;

import java.time.LocalDateTime;

public class TicketDTO {

    public TicketDTO() {
    }

    private TicketType ticketType;

    private LocalDateTime stampStart;

    private Long id;

    private String carNumberPlate;

    public TicketType getTicketType() {
        return ticketType;
    }

    public void setTicketType(TicketType ticketType) {
        this.ticketType = ticketType;
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

    public String getCarNumberPlate() {
        return carNumberPlate;
    }

    public void setCarNumberPlate(String carNumerPlate) {
        this.carNumberPlate = carNumerPlate;
    }
}
