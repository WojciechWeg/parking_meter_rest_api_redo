package com.wojtek.parkingmeter.model;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.wojtek.parkingmeter.helpers.ChargeCalculator;
import com.wojtek.parkingmeter.helpers.enums.TicketType;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;

@Entity
@Table(name = "tickets")
public class TicketEntity {

    public TicketEntity() {
    }

    public TicketEntity(TicketType ticketType, LocalDateTime stampStart, LocalDateTime stampStop, String carNumberPlate) {
        this.ticketType = ticketType;
        this.stampStart = stampStart;
        this.stampStop = stampStop;
        this.carNumberPlate = carNumberPlate;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ticket_type")
    private TicketType ticketType;

    @Column(name = "charge")
    private BigDecimal charge;

    @Column(name = "stamp_start")
    private LocalDateTime stampStart;

    @Column(name = "stamp_stop")
    private LocalDateTime stampStop;

    @Column(name="car_number_plate")
    private String carNumberPlate;

    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "cars_id")
    private CarEntity carEntity;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TicketType getTicketType() {
        return ticketType;
    }

    public void setTicketType(TicketType ticketType) {
        this.ticketType = ticketType;
    }

    public BigDecimal getCharge() {
        return charge;
    }

    public void setCharge(BigDecimal charge) {
        this.charge = charge;
    }

    public LocalDateTime getStampStart() {
        return stampStart;
    }

    public void setStampStart(LocalDateTime stampStart) {
        this.stampStart = stampStart;
    }

    public LocalDateTime getStampStop() {
        return stampStop;
    }

    public void setStampStop(LocalDateTime stampStop) {
        this.stampStop = stampStop;
    }

    public CarEntity getCarEntity() {
        return carEntity;
    }

    public void setCarEntity(CarEntity carEntity) {
        this.carEntity = carEntity;
    }

    public void countCharge() {
        setCharge(ChargeCalculator.charge(getTicketType(), getDuration()));
    }

    public Duration getDuration() {
        return Duration.between(stampStart, stampStop);
    }

    public String getCarNumberPlate() {
        return carNumberPlate;
    }

    public void setCarNumberPlate(String carNumberPlate) {
        this.carNumberPlate = carNumberPlate;
    }
}
