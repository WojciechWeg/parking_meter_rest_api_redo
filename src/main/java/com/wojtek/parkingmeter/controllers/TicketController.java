package com.wojtek.parkingmeter.controllers;

import com.wojtek.parkingmeter.helpers.*;
import com.wojtek.parkingmeter.model.DTO.TicketDTO;
import com.wojtek.parkingmeter.model.DTO.TicketStarterDTO;
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

    @PostMapping("/tickets")
    public ResponseEntity<TicketDTO> startTicket(@RequestBody TicketStarterDTO ticketStarterDTO) {

        if (validator.validateNewTicket(ticketStarterDTO.getTicketType().toString(), ticketStarterDTO.getCarNumberPlate()))
            return ResponseEntity.ok().body(ticketService.startTicket(ticketStarterDTO.getTicketType().toString(), ticketStarterDTO.getCarNumberPlate()));
        else
            return ResponseEntity.badRequest().body(new TicketDTO());

    }

    @PutMapping("/tickets/{id}")
    public ResponseEntity<String> stopTicket(@PathVariable String id) {

        if(validator.checkIfExists(id)) {
            ticketService.stopTicket(Long.parseLong(id));
            return ResponseEntity.ok().body(validator.validateStopTicket(id));
        }
        else
            return ResponseEntity.ok().body(validator.validateStopTicket(id));

    }

    @GetMapping("/tickets/{id}/charge")
    public ResponseEntity<BigDecimal> checkCharge(@PathVariable String id) {

        if(validator.checkIfExists(id))
            return ResponseEntity.ok(ticketService.checkCharge(Long.parseLong(id)));
        else
            return ResponseEntity.badRequest().body(BigDecimal.valueOf(-1.0));

    }

    @GetMapping("tickets/sum")
    public ResponseEntity<BigDecimal> checkSum() {
        return ResponseEntity.ok(ticketService.checkSum());
    }


}