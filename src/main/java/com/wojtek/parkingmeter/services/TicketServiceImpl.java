package com.wojtek.parkingmeter.services;

import com.wojtek.parkingmeter.helpers.*;
import com.wojtek.parkingmeter.helpers.enums.TicketType;
import com.wojtek.parkingmeter.mapper.TicketMapper;
import com.wojtek.parkingmeter.model.CarEntity;
import com.wojtek.parkingmeter.model.TicketEntity;

import com.wojtek.parkingmeter.model.DTO.TicketDTO;
import com.wojtek.parkingmeter.repositories.CarRepository;
import com.wojtek.parkingmeter.repositories.TicketRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;
    private final CarRepository carRepository;
    private final TicketMapper ticketMapper;
    private final JdbcTemplate jdbcTemplate;

    public TicketServiceImpl(TicketRepository ticketRepository, CarRepository carRepository, TicketMapper ticketMapper, JdbcTemplate jdbcTemplate) {
        this.ticketRepository = ticketRepository;
        this.carRepository = carRepository;
        this.ticketMapper = ticketMapper;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public TicketDTO startTicket(String ticketType, String numberPlate) {

        TicketEntity newTicketEntity = new TicketEntity(TicketType.valueOf(ticketType.toUpperCase()), LocalDateTime.now(), LocalDateTime.of(0, 1, 1, 0, 0, 0, 0));
        CarEntity carEntity = new CarEntity(numberPlate);
        carEntity.addTicket(newTicketEntity);
        carRepository.save(carEntity);

        ticketRepository.save(newTicketEntity);

        return ticketMapper.ticketToTicketDTO(newTicketEntity);
    }

    @Override
    public TicketEntity stopTicket(Long id) {

        Optional<TicketEntity> stopTicketOpt = ticketRepository.findById(id);

        TicketEntity stopTicketEntity = stopTicketOpt.get();

        stopTicketEntity.setStampStop(LocalDateTime.now());

        stopTicketEntity.countCharge();


        // gdy nie ma biletu o takim id

        if (stopTicketEntity.getCarEntity() != null) {
            Long carID = stopTicketEntity.getCarEntity().getId();
            carRepository.deleteById(carID);
        }


        stopTicketEntity.setCarEntity(null);

        return ticketRepository.save(stopTicketEntity);

    }

    @Override
    public double checkCharge(Long id) {

        Optional<TicketEntity> ticketOptional = ticketRepository.findById(id);


        TicketEntity ticketEntity = ticketOptional.get();

        if (!(ticketEntity.getCarEntity() == null))
            ticketEntity.setStampStop(LocalDateTime.now());

        TicketType ticketType = ticketEntity.getTicketType();


        return ChargeCalculator.charge(ticketType, ticketEntity.getDuration());

    }

    @Override
    public double checkSum() {

       return jdbcTemplate.queryForObject("SELECT sum(charge) FROM TICKETS", Double.class);
        
    }

    @Override
    public boolean hasStarted(String numberPlate) {

        boolean hasStarted = true;

        int id = 0;

        try {
            // pobierz id samochodu którego znamy numery tablicy rejestracujnej
            id = jdbcTemplate.queryForObject(
                    "SELECT ID FROM CARS WHERE nr_plate = \'" + numberPlate + "\'", Integer.class);
            if (id == 0)
                hasStarted = false;
        } catch (EmptyResultDataAccessException e) {
            hasStarted = false;
        }

        if (id != 0) {
            Integer ticketID = 0;
            // pobierzmy id biletu takiego samochodu
            try {
                ticketID = jdbcTemplate.queryForObject("SELECT ID FROM TICKETS WHERE CAR_ID = " + id + "", Integer.class);
            } catch (EmptyResultDataAccessException e) {
                // jesli ticketID == null to znaczy że auto jest na parkingu bez biletu
                if (ticketID.equals(0))
                    hasStarted = false;
                // jeśłi ticketID != null to znaczy że auto ma bilet

            }

            if (!ticketID.equals(0))
                hasStarted = true;
        }

        return hasStarted;
    }
}
