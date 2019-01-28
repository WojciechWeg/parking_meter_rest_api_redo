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

    public CarEntity(String nr_plate) {

        this.nr_plate = nr_plate;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nr_plate")
    private String nr_plate;

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

    public String getNr_plate() {
        return nr_plate;
    }

    public void setNr_plate(String nr_plate) {
        this.nr_plate = nr_plate;
    }

    public List<TicketEntity> getTicketEntities() {
        return ticketEntities;
    }

    public void setTicketEntities(List<TicketEntity> ticketEntities) {
        this.ticketEntities = ticketEntities;
    }
}
