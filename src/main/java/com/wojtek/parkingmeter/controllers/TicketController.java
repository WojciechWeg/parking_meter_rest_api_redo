package com.wojtek.parkingmeter.controllers;

import com.wojtek.parkingmeter.helpers.*;
import com.wojtek.parkingmeter.model.DTO.TicketDTO;
import com.wojtek.parkingmeter.services.TicketService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.math.BigDecimal;


@RestController
public class TicketController {

    private final TicketService ticketService;
    private final Validator validator;

    public TicketController(TicketService ticketService, Validator validator) {
        this.ticketService = ticketService;
        this.validator = validator;
    }

    @GetMapping("/start/{ticketType}/{numberPlate}")
    public ResponseEntity<TicketDTO> startTicket(@PathVariable String ticketType, @PathVariable String numberPlate) {

        if (validator.validateNewTicket(ticketType, numberPlate))
            return ResponseEntity.ok().body(ticketService.startTicket(ticketType, numberPlate));
        else
            return ResponseEntity.badRequest().body(new TicketDTO());

    }

    @GetMapping("/stop/{id}")
    public ResponseEntity<String> stopTicket(@PathVariable String id) {

        return ResponseEntity.ok().body(validator.validateStopTicket(id));

    }

    @GetMapping("/check_charge/{id}")
    public ResponseEntity<BigDecimal> checkCharge(@PathVariable String id) {

        if(validator.checkIfExists(id))
            return ResponseEntity.ok(ticketService.checkCharge(Long.parseLong(id)));
        else
            return ResponseEntity.badRequest().body(BigDecimal.valueOf(-1.0));

    }

    @GetMapping("/sum")
    public ResponseEntity<BigDecimal> checkSum() {
        return ResponseEntity.ok(ticketService.checkSum());
    }

    @GetMapping("/hasStarted/{numberPlate}")
    public ResponseEntity<Boolean> hasStarted(@PathVariable String numberPlate) {

        if(validator.checkNumberPlate(numberPlate))
            return ResponseEntity.ok(ticketService.hasStarted(numberPlate));

        return ResponseEntity.ok(false);
    }
}