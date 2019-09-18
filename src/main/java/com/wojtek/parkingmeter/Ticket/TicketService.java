package com.wojtek.parkingmeter.Ticket;

import com.wojtek.parkingmeter.Car.CarEntity;

import com.wojtek.parkingmeter.Car.CarRepository;
import com.wojtek.parkingmeter.exceptions.TicketDoesNotExistException;
import com.wojtek.parkingmeter.helpers.Validator;
import com.wojtek.parkingmeter.helpers.calcs.ChargeCalculator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;
    private final CarRepository carRepository;
    private final TicketMapper ticketMapper;
    private final Logger logger;
    private final ChargeCalculator chargeCalculator;
    private final Validator validator;

    public TicketService(TicketRepository ticketRepository, CarRepository carRepository, TicketMapper ticketMapper, ChargeCalculator chargeCalculator, Validator validator) {
        this.ticketRepository = ticketRepository;
        this.carRepository = carRepository;
        this.ticketMapper = ticketMapper;
        this.chargeCalculator = chargeCalculator;
        this.validator = validator;
        logger = LoggerFactory.getLogger(TicketService.class);
    }

    public TicketDTO startTicket(String ticketType, String numberPlate) {

        validator.validateNewTicket(ticketType, numberPlate);

        TicketEntity newTicketEntity = new TicketEntity(TicketType.valueOf(ticketType.toUpperCase()), LocalDateTime.now(), numberPlate);
        logger.info("new ticket entity id" + newTicketEntity.getId());
        CarEntity carEntity = new CarEntity(numberPlate);
        carEntity.addTicket(newTicketEntity);
        carRepository.save(carEntity);
        newTicketEntity.setCarEntity(carEntity);
        ticketRepository.save(newTicketEntity);

        return ticketMapper.ticketToTicketDTO(newTicketEntity);
    }


    public TicketStopDTO stopTicket(String id) {

        validator.isIdLong(id);

        //tutaj sprawdź czy bilet już został zatzymany czy nie

        Optional<TicketEntity> stopTicketOpt = ticketRepository.findById(Long.parseLong(id));

        stopTicketOpt.orElseThrow(() ->  new TicketDoesNotExistException("Ticket with given ID does not exist: " + id));

        TicketEntity stopTicketEntity = stopTicketOpt.get();

        stopTicketEntity.setStampStop(LocalDateTime.now());
        logger.info("Charge for ticked id: " + id + " is " + chargeCalculator.charge(stopTicketEntity.getTicketType(),stopTicketEntity.getDuration()));
        stopTicketEntity.setCharge(chargeCalculator.charge(stopTicketEntity.getTicketType(),stopTicketEntity.getDuration()));

        CarEntity carEntity = stopTicketEntity.getCarEntity();
        if (carEntity != null) {
            Long carID = carEntity.getId();
            carRepository.deleteById(carID);
            logger.info("CarID: " + carID);

        }

        stopTicketEntity.setCarEntity(null);
        logger.info("stopTicketEntity id" + stopTicketEntity.getId());
        ticketRepository.save(stopTicketEntity);

        return ticketMapper.ticketEntityToTicketPutDTO(stopTicketEntity);

    }


    public BigDecimal checkCharge(String id) {

        validator.isIdLong(id);

        Optional<TicketEntity> ticketOptional = ticketRepository.findById(Long.parseLong(id));

        ticketOptional.orElseThrow(() -> new TicketDoesNotExistException("Ticket with given ID does not exist: " + id));

        TicketEntity ticketEntity = ticketOptional.get();

        if (ticketEntity.getCarEntity() != null)
            ticketEntity.setStampStop(LocalDateTime.now());

        TicketType ticketType = ticketEntity.getTicketType();

        logger.info("Charge for ticked id: " + id + " is " + chargeCalculator.charge(ticketType, ticketEntity.getDuration()));
        return chargeCalculator.charge(ticketType, ticketEntity.getDuration());

    }


    public BigDecimal checkSum() {

        return ticketRepository.returnSumOfAllTickets().setScale(2);

    }


}
