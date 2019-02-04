package com.wojtek.parkingmeter.Car;

import com.wojtek.parkingmeter.Ticket.TicketEntity;

import javax.persistence.*;

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

    @OneToOne(mappedBy = "carEntity", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private TicketEntity ticket;

    public void addTicket(TicketEntity ticket) {
        this.ticket = ticket;
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

    public TicketEntity getTicket() {
        return ticket;
    }

    public void setTicket(TicketEntity ticket) {
        this.ticket = ticket;
    }
}
