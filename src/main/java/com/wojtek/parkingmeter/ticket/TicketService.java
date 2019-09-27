package com.wojtek.parkingmeter.ticket;

import com.wojtek.parkingmeter.car.CarEntity;

import com.wojtek.parkingmeter.car.CarRepository;
import com.wojtek.parkingmeter.exceptions.TicketStoppedException;
import com.wojtek.parkingmeter.profit.ProfitEntity;
import com.wojtek.parkingmeter.profit.ProfitRepository;
import com.wojtek.parkingmeter.exceptions.TicketDoesNotExistException;
import com.wojtek.parkingmeter.helpers.Validator;
import com.wojtek.parkingmeter.helpers.calcs.ChargeCalculator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;
    private final CarRepository carRepository;
    private final ProfitRepository profitRepository;
    private final TicketMapper ticketMapper;
    private final Logger logger;
    private final ChargeCalculator chargeCalculator;
    private final Validator validator;

    public TicketService(TicketRepository ticketRepository, CarRepository carRepository, ProfitRepository profitRepository, TicketMapper ticketMapper, ChargeCalculator chargeCalculator, Validator validator) {
        this.ticketRepository = ticketRepository;
        this.carRepository = carRepository;
        this.profitRepository = profitRepository;
        this.ticketMapper = ticketMapper;
        this.chargeCalculator = chargeCalculator;
        this.validator = validator;
        logger = LoggerFactory.getLogger(TicketService.class);
    }

    public TicketDTO startTicket(String ticketType, String numberPlate) {

        validator.validateNewTicket(ticketType, numberPlate);

        TicketEntity newTicketEntity = new TicketEntity(TicketType.valueOf(ticketType.toUpperCase()), LocalDateTime.now(), numberPlate);

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

        if(stopTicketEntity.getStampStop()!=null)
            throw new TicketStoppedException("Ticket stopped already");

        stopTicketEntity.setStampStop(LocalDateTime.now());
        logger.info("Charge for ticked id: " + id + " is " + chargeCalculator.charge(stopTicketEntity.getTicketType(),stopTicketEntity.getDuration()));

        BigDecimal charge = chargeCalculator.charge(stopTicketEntity.getTicketType(),stopTicketEntity.getDuration());

        stopTicketEntity.setCharge(charge);

        //tutaj zapisz income
        ProfitEntity profitEntity = new ProfitEntity();
        //charge
        profitEntity.setIncome(charge);
        //date
        profitEntity.setDate(LocalDate.now());
        //save
        profitRepository.save(profitEntity);


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

}
