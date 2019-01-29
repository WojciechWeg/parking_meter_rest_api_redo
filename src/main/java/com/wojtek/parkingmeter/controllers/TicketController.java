package com.wojtek.parkingmeter.controllers;

import com.wojtek.parkingmeter.helpers.*;
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

    @GetMapping("/start/{ticketType}/{numberPlate}")
    public ResponseEntity<TicketDTO> startTicket(@PathVariable String ticketType, @PathVariable String numberPlate) {


        if (ticketService.hasStarted(numberPlate))
            return ResponseEntity.status(HttpStatus.IM_USED).body(new TicketDTO()); // tutaj bym zwrócił info że takkie auto ma już bilet. Użyłem HttpStatus.IM_USED, ale chyba to nie do tego.
        if (Validator.validateNewTicket(ticketType, numberPlate))
            return ResponseEntity.ok().body(ticketService.startTicket(ticketType, numberPlate));
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
    public ResponseEntity<Double> checkCharge(@PathVariable Long id) {

        try {
            return ResponseEntity.ok(ticketService.checkCharge(id));
        } catch (NoSuchElementException e) {
            return ResponseEntity.badRequest().body(-1.0); // tutaj była zmiana z String na Double stąd body ustawiona na -1 zmiast "NO SUCH TICKET"
        }

    }

    @GetMapping("/sum")
    public ResponseEntity<Double> checkSum() {
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