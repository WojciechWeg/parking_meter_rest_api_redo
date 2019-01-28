package com.wojtek.parkingmeter.controllers;

import com.wojtek.parkingmeter.helpers.*;
import com.wojtek.parkingmeter.helpers.enums.HasStartedEnum;
import com.wojtek.parkingmeter.model.DTO.HasStarted;
import com.wojtek.parkingmeter.model.DTO.Sum;
import com.wojtek.parkingmeter.model.DTO.TicketDTO;
import com.wojtek.parkingmeter.services.TicketService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
public class TicketController {

    private final JdbcTemplate jdbcTemplate;
    private final TicketService ticketService;

    public TicketController(JdbcTemplate jdbcTemplate, TicketService ticketService) {
        this.jdbcTemplate = jdbcTemplate;
        this.ticketService = ticketService;
    }

    @GetMapping("/start/{ticket_type}/{nr_plate}")
    public ResponseEntity<TicketDTO> startTicket(@PathVariable String ticket_type, @PathVariable String nr_plate) {


        if (ticketService.hasStarted(nr_plate).getHasStarted().equals(HasStartedEnum.YES))
            return ResponseEntity.status(HttpStatus.IM_USED).body(new TicketDTO()); // tutaj bym zwrócił info że takkie auto ma już bilet. Użyłem HttpStatus.IM_USED, ale chyba to nie do tego.
        if (Validator.validateNewTicket(ticket_type, nr_plate))
            return ResponseEntity.ok().body(ticketService.startTicket(ticket_type, nr_plate));
        else
            return ResponseEntity.badRequest().body(new TicketDTO());

    }

    @GetMapping("/stop/{id}")
    public ResponseEntity<String> stopTicket(@PathVariable Long id) {

        if (!Validator.checkIfAlreadyStarted(jdbcTemplate, id))
            return ResponseEntity.ok().body("TICKET ALREADY STOPPED");
        if (!Validator.checkIfExists(jdbcTemplate, id))
            return ResponseEntity.badRequest().body("TICKET DOES NOT EXIST");

        try {
            ticketService.stopTicket(id);
            return ResponseEntity.ok().body("TICKET STOPPED");
        } catch (NoSuchElementException e) {
            return ResponseEntity.badRequest().body("TICKET DOES NOT EXIST");
        }

    }

    @GetMapping("/check_charge/{id}")
    public ResponseEntity<String> checkCharge(@PathVariable Long id) {

        try {
            return ResponseEntity.ok(ticketService.checkCharge(id));
        } catch (NoSuchElementException e) {
            String noSuchTicket = "TICKET DOES NOT EXIST";
            return ResponseEntity.badRequest().body(noSuchTicket);
        }

    }

    @GetMapping("/sum")
    public ResponseEntity<Sum> checkSum() {
        return ResponseEntity.ok(ticketService.checkSum());
    }

    @GetMapping("/hasStarted/{nr_plate}")
    public ResponseEntity<HasStarted> hasStarted(@PathVariable String nr_plate) {

        if (nr_plate.length() != 5) {
            HasStarted hsj = new HasStarted(HasStartedEnum.INVALID_NR_PLATE);
            return ResponseEntity.badRequest().body(hsj);
        }

        return ResponseEntity.ok(ticketService.hasStarted(nr_plate));

    }
}