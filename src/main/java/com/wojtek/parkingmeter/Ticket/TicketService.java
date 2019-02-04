package com.wojtek.parkingmeter.Ticket;

import com.wojtek.parkingmeter.helpers.*;
import com.wojtek.parkingmeter.Car.CarEntity;

import com.wojtek.parkingmeter.Car.CarRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;
    private final CarRepository carRepository;
    private final TicketMapper ticketMapper;
    private final Logger logger;

    public TicketService(TicketRepository ticketRepository, CarRepository carRepository, TicketMapper ticketMapper) {
        this.ticketRepository = ticketRepository;
        this.carRepository = carRepository;
        this.ticketMapper = ticketMapper;
        logger = LoggerFactory.getLogger(TicketService.class);

    }


    public TicketDTO startTicket(String ticketType, String numberPlate) {

        TicketEntity newTicketEntity = new TicketEntity(TicketType.valueOf(ticketType.toUpperCase()), LocalDateTime.now(), LocalDateTime.of(0, 1, 1, 0, 0, 0, 0), numberPlate);
        CarEntity carEntity = new CarEntity(numberPlate);
        carEntity.addTicket(newTicketEntity);
        carRepository.save(carEntity);
        newTicketEntity.setCarEntity(carEntity);
        ticketRepository.save(newTicketEntity);

        return ticketMapper.ticketToTicketDTO(newTicketEntity);
    }


    public TicketEntity stopTicket(Long id) {

        Optional<TicketEntity> stopTicketOpt = ticketRepository.findById(id);

        TicketEntity stopTicketEntity;

        if(stopTicketOpt.isPresent())
             stopTicketEntity = stopTicketOpt.get();
        else
            throw new NoSuchElementException();

        stopTicketEntity.setStampStop(LocalDateTime.now());

        stopTicketEntity.countCharge();

        if (stopTicketEntity.getCarEntity() != null) {
            Long carID = stopTicketEntity.getCarEntity().getId();
            //carRepository.deleteById(carID); // usuwanie przez ID też nie działa
            carRepository.delete(stopTicketEntity.getCarEntity());

            logger.info("CarID: " + carID);
            stopTicketEntity.setCarEntity(null);
        }

        return ticketRepository.save(stopTicketEntity);

    }


    public BigDecimal checkCharge(Long id) {

        Optional<TicketEntity> ticketOptional = ticketRepository.findById(id);

        TicketEntity ticketEntity;

        if(ticketOptional.isPresent())
            ticketEntity = ticketOptional.get();
        else
            throw new NoSuchElementException();

        if (ticketEntity.getCarEntity() != null)
            ticketEntity.setStampStop(LocalDateTime.now());

        TicketType ticketType = ticketEntity.getTicketType();


        return ChargeCalculator.charge(ticketType, ticketEntity.getDuration());

    }


    public BigDecimal checkSum() {

        return ticketRepository.returnSumOfAllTickets().setScale(2);

    }


}