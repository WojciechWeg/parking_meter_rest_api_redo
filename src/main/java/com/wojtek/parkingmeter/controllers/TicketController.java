package com.wojtek.parkingmeter.controllers;

import com.wojtek.parkingmeter.helpers.*;
import com.wojtek.parkingmeter.model.DTO.TicketDTO;
import com.wojtek.parkingmeter.services.TicketService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.math.BigDecimal;
import java.util.NoSuchElementException;

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


        if (ticketService.hasStarted(numberPlate))
            return ResponseEntity.status(HttpStatus.IM_USED).body(new TicketDTO()); // tutaj bym zwrócił info że takkie auto ma już bilet. Użyłem HttpStatus.IM_USED, ale chyba to nie do tego.
        if (validator.validateNewTicket(ticketType, numberPlate))
            return ResponseEntity.ok().body(ticketService.startTicket(ticketType, numberPlate));
        else
            return ResponseEntity.badRequest().body(new TicketDTO());

    }

    @GetMapping("/stop/{id}")
    public ResponseEntity<String> stopTicket(@PathVariable Long id) {

        if (!validator.checkIfAlreadyStarted(id))
            return ResponseEntity.ok().body("TICKET ALREADY STOPPED");
        if (!validator.checkIfExists(id))
            return ResponseEntity.badRequest().body("TICKET DOES NOT EXIST");

        try {
            ticketService.stopTicket(id);
            return ResponseEntity.ok().body("TICKET STOPPED");
        } catch (NoSuchElementException e) {
            return ResponseEntity.badRequest().body("TICKET DOES NOT EXIST");
        }

    }

    @GetMapping("/check_charge/{id}")
    public ResponseEntity<BigDecimal> checkCharge(@PathVariable Long id) {

        try {
            return ResponseEntity.ok(ticketService.checkCharge(id));
        } catch (NoSuchElementException e) {
            return ResponseEntity.badRequest().body(BigDecimal.valueOf(-1.0)); // tutaj była zmiana z String na Double stąd body ustawiona na -1 zmiast "NO SUCH TICKET"

        }

    }

    @GetMapping("/sum")
    public ResponseEntity<BigDecimal> checkSum() {
        return ResponseEntity.ok(ticketService.checkSum());
    }

    @GetMapping("/hasStarted/{numberPlate}")
    public ResponseEntity<Boolean> hasStarted(@PathVariable String numberPlate) {

        if (numberPlate.length() != 5) {
            return ResponseEntity.badRequest().body(false);
        }

        return ResponseEntity.ok(ticketService.hasStarted(numberPlate));

    }
}