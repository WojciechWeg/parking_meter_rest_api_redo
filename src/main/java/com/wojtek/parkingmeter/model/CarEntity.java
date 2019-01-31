package com.wojtek.parkingmeter.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cars")
public class CarEntity {

    protected CarEntity() {
    }

    public CarEntity(String nrPlate) {

        this.nrPlate = nrPlate;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nr_plate")
    private String nrPlate;

    @OneToMany(mappedBy = "carEntity", cascade = {CascadeType.ALL})
    @JsonIgnoreProperties(value = {"carEntity"})
    private List<TicketEntity> ticketEntities;

    public void addTicket(TicketEntity ticketEntity) {
        if (ticketEntities == null)
            ticketEntities = new ArrayList<>();

        this.ticketEntities.add(ticketEntity);

        ticketEntity.setCarEntity(this);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNrPlate() {
        return nrPlate;
    }

    public void setNrPlate(String nrPlate) {
        this.nrPlate = nrPlate;
    }

    public List<TicketEntity> getTicketEntities() {
        return ticketEntities;
    }

    public void setTicketEntities(List<TicketEntity> ticketEntities) {
        this.ticketEntities = ticketEntities;
    }
}
