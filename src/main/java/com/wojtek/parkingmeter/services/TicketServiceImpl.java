package com.wojtek.parkingmeter.services;

import com.wojtek.parkingmeter.helpers.*;
import com.wojtek.parkingmeter.helpers.enums.TicketType;
import com.wojtek.parkingmeter.mapper.TicketMapper;
import com.wojtek.parkingmeter.model.CarEntity;
import com.wojtek.parkingmeter.model.TicketEntity;

import com.wojtek.parkingmeter.model.DTO.TicketDTO;
import com.wojtek.parkingmeter.repositories.CarRepository;
import com.wojtek.parkingmeter.repositories.TicketRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;
    private final CarRepository carRepository;
    private final TicketMapper ticketMapper;

    public TicketServiceImpl(TicketRepository ticketRepository, CarRepository carRepository, TicketMapper ticketMapper) {
        this.ticketRepository = ticketRepository;
        this.carRepository = carRepository;
        this.ticketMapper = ticketMapper;
    }

    @Override
    public TicketDTO startTicket(String ticketType, String numberPlate) {

        TicketEntity newTicketEntity = new TicketEntity(TicketType.valueOf(ticketType.toUpperCase()), LocalDateTime.now(), LocalDateTime.of(0, 1, 1, 0, 0, 0, 0), numberPlate);
        CarEntity carEntity = new CarEntity(numberPlate);
        carEntity.addTicket(newTicketEntity);
        carRepository.save(carEntity);

        ticketRepository.save(newTicketEntity);

        return ticketMapper.ticketToTicketDTO(newTicketEntity);
    }

    @Override
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
            carRepository.deleteById(carID);
            stopTicketEntity.setCarEntity(null);
        }

        return ticketRepository.save(stopTicketEntity);

    }

    @Override
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

    @Override
    public BigDecimal checkSum() {

        return ticketRepository.returnSumOfAllTickets().setScale(2);

    }


}
