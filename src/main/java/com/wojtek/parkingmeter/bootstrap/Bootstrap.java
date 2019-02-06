package com.wojtek.parkingmeter.bootstrap;

import com.wojtek.parkingmeter.helpers.calcs.ChargeCalculator;
import com.wojtek.parkingmeter.Ticket.TicketType;
import com.wojtek.parkingmeter.Car.CarEntity;
import com.wojtek.parkingmeter.Ticket.TicketEntity;
import com.wojtek.parkingmeter.Car.CarRepository;
import com.wojtek.parkingmeter.Ticket.TicketRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class Bootstrap implements CommandLineRunner {

    private final TicketRepository ticketRepository;
    private final CarRepository carRepository;
    private final ChargeCalculator chargeCalculator;

    public Bootstrap(TicketRepository ticketRepository, CarRepository carRepository, ChargeCalculator chargeCalculator) {

        this.ticketRepository = ticketRepository;
        this.carRepository = carRepository;
        this.chargeCalculator = chargeCalculator;
    }


    @Override
    public void run(String... args) throws Exception {

        loadTickets();

        loadCars();
    }

    private void loadCars() {
        CarEntity carEntity1 = new CarEntity("00000");

        carRepository.save(carEntity1);
    }

    private void loadTickets() {
        TicketEntity ticketEntity1 = new TicketEntity(TicketType.REGULAR, LocalDateTime.of(2019, 1, 13, 19, 00, 00, 00), LocalDateTime.of(2019, 1, 13, 19, 30, 00, 00), "WT321");

        ticketEntity1.setCharge(chargeCalculator.charge(ticketEntity1.getTicketType(), ticketEntity1.getDuration()));

        ticketRepository.save(ticketEntity1);

        TicketEntity ticketEntity2 = new TicketEntity(TicketType.REGULAR, LocalDateTime.of(2019, 1, 13, 19, 00, 00, 00), LocalDateTime.of(2019, 1, 13, 20, 01, 00, 00),"43EWS");

        ticketEntity2.setCharge(chargeCalculator.charge(ticketEntity2.getTicketType(), ticketEntity2.getDuration()));

        ticketRepository.save(ticketEntity2);


        TicketEntity ticketEntity3 = new TicketEntity(TicketType.REGULAR, LocalDateTime.of(2019, 1, 13, 19, 00, 00, 00), LocalDateTime.of(2019, 1, 13, 21, 01, 00, 00),"LO456");

        ticketEntity3.setCharge(chargeCalculator.charge(ticketEntity3.getTicketType(), ticketEntity3.getDuration()));

        ticketRepository.save(ticketEntity3);

        TicketEntity ticketEntity4 = new TicketEntity(TicketType.REGULAR, LocalDateTime.of(2019, 1, 13, 19, 00, 00, 00), LocalDateTime.of(2019, 1, 13, 22, 01, 00, 00),"PO990");

        ticketEntity4.setCharge(chargeCalculator.charge(ticketEntity4.getTicketType(), ticketEntity4.getDuration()));

        ticketRepository.save(ticketEntity4);


        TicketEntity ticketEntity5 = new TicketEntity(TicketType.DISABLED, LocalDateTime.of(2019, 1, 13, 19, 00, 00, 00), LocalDateTime.of(2019, 1, 13, 19, 30, 00, 00),"ER109");

        ticketEntity5.setCharge(chargeCalculator.charge(ticketEntity5.getTicketType(), ticketEntity5.getDuration()));

        ticketRepository.save(ticketEntity5);

        TicketEntity ticketEntity6 = new TicketEntity(TicketType.DISABLED, LocalDateTime.of(2019, 1, 13, 19, 00, 00, 00), LocalDateTime.of(2019, 1, 13, 20, 01, 00, 00),"NO890");

        ticketEntity6.setCharge(chargeCalculator.charge(ticketEntity6.getTicketType(), ticketEntity6.getDuration()));

        ticketRepository.save(ticketEntity6);


        TicketEntity ticketEntity7 = new TicketEntity(TicketType.DISABLED, LocalDateTime.of(2019, 1, 13, 19, 00, 00, 00), LocalDateTime.of(2019, 1, 13, 21, 01, 00, 00),"BT345");

        ticketEntity7.setCharge(chargeCalculator.charge(ticketEntity7.getTicketType(), ticketEntity7.getDuration()));

        ticketRepository.save(ticketEntity7);

        TicketEntity ticketEntity8 = new TicketEntity(TicketType.DISABLED, LocalDateTime.of(2019, 1, 13, 19, 00, 00, 00), LocalDateTime.of(2019, 1, 13, 22, 01, 00, 00),"ER000");

        ticketEntity8.setCharge(chargeCalculator.charge(ticketEntity8.getTicketType(), ticketEntity8.getDuration()));

        ticketRepository.save(ticketEntity8);
    }
}