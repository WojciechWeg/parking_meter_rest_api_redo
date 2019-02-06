package com.wojtek.parkingmeter.Ticket;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TicketStopDTO {

    private TicketType ticketType;

    private LocalDateTime stampStart;

    private LocalDateTime stampStop;

    private Long id;

    private String carNumberPlate;

    private BigDecimal charge;

    public TicketStopDTO() {
    }

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

    public void setCarNumberPlate(String carNumberPlate) {
        this.carNumberPlate = carNumberPlate;
    }

    public BigDecimal getCharge() {
        return charge;
    }

    public void setCharge(BigDecimal charge) {
        this.charge = charge;
    }

    public LocalDateTime getStampStop() {
        return stampStop;
    }

    public void setStampStop(LocalDateTime stampStop) {
        this.stampStop = stampStop;
    }
}
