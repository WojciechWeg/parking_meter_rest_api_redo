package com.wojtek.parkingmeter.Ticket;
import com.wojtek.parkingmeter.Car.CarEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;

@Entity
@Table(name = "tickets")
@Setter
@Getter
@NoArgsConstructor
public class TicketEntity {


    public TicketEntity(TicketType ticketType, LocalDateTime stampStart, LocalDateTime stampStop, String carNumberPlate) {
        this.ticketType = ticketType;
        this.stampStart = stampStart;
        this.stampStop = stampStop;
        this.carNumberPlate = carNumberPlate;
    }

    public TicketEntity(TicketType ticketType, LocalDateTime stampStart, String carNumberPlate) {
        this.ticketType = ticketType;
        this.stampStart = stampStart;
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

    public Duration getDuration() {
        return Duration.between(stampStart, stampStop);
    }

}
