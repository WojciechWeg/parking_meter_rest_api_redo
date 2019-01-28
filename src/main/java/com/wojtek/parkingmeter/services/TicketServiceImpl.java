package com.wojtek.parkingmeter.services;

import com.wojtek.parkingmeter.helpers.*;
import com.wojtek.parkingmeter.helpers.enums.HasStartedEnum;
import com.wojtek.parkingmeter.helpers.enums.TicketType;
import com.wojtek.parkingmeter.mapper.TicketMapper;
import com.wojtek.parkingmeter.model.CarEntity;
import com.wojtek.parkingmeter.model.DTO.HasStarted;
import com.wojtek.parkingmeter.model.DTO.Sum;
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
    public TicketDTO startTicket(String ticket_type, String nr_plate) {

        TicketEntity newTicketEntity = new TicketEntity(TicketType.valueOf(ticket_type.toUpperCase()), LocalDateTime.now(), LocalDateTime.of(0, 1, 1, 0, 0, 0, 0));
        CarEntity carEntity = new CarEntity(nr_plate);
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
            Long id_car = stopTicketEntity.getCarEntity().getId();
            carRepository.deleteById(id_car);
        }


        stopTicketEntity.setCarEntity(null);

        return ticketRepository.save(stopTicketEntity);

    }

    @Override
    public String checkCharge(Long id) {

        Optional<TicketEntity> ticketOptional = ticketRepository.findById(id);


        TicketEntity ticketEntity = ticketOptional.get();

        if (!(ticketEntity.getCarEntity() == null))
            ticketEntity.setStampStop(LocalDateTime.now());

        TicketType ticketType = ticketEntity.getTicketType();


        return Double.toString(ChargeCalculator.charge(ticketType, ticketEntity.getDuration()));

    }

    @Override
    public Sum checkSum() {

        Double sum = jdbcTemplate.queryForObject("SELECT sum(charge) FROM TICKETS", Double.class);
        Sum sumJSON = new Sum(sum);

        return sumJSON;
    }

    @Override
    public HasStarted hasStarted(String nr_plate) {

        HasStarted hasStarted = new HasStarted(HasStartedEnum.YES);

        int id = 0;

        try {
            // pobierz id samochodu którego znamy numery tablicy rejestracujnej
            id = jdbcTemplate.queryForObject(
                    "SELECT ID FROM CARS WHERE NR_PLATE = \'" + nr_plate + "\'", Integer.class);
            if (id == 0)
                hasStarted.setHasStarted(HasStartedEnum.NO);
        } catch (EmptyResultDataAccessException e) {
            hasStarted.setHasStarted(HasStartedEnum.NO);
        }

        if (id != 0) {
            Integer ticketID = 0;
            // pobierzmy id biletu takiego samochodu
            try {
                ticketID = jdbcTemplate.queryForObject("SELECT ID FROM TICKETS WHERE CAR_ID = " + id + "", Integer.class);
            } catch (EmptyResultDataAccessException e) {
                // jesli ticketID == null to znaczy że auto jest na parkingu bez biletu
                if (ticketID.equals(0))
                    hasStarted.setHasStarted(HasStartedEnum.NO);
                // jeśłi ticketID != null to znaczy że auto ma bilet

            }

            if (!ticketID.equals(0))
                hasStarted.setHasStarted(HasStartedEnum.YES);
        }

        return hasStarted;
    }
}
