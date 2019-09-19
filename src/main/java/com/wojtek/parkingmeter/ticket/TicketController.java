package com.wojtek.parkingmeter.ticket;

import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;

@RestController
public class TicketController {

    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @PostMapping("/tickets")
    public TicketDTO startTicket(@RequestBody TicketStartDTO ticketStartDTO) {
        return ticketService.startTicket(ticketStartDTO.getTicketType().toString(), ticketStartDTO.getCarNumberPlate());
    }

    @PutMapping("/tickets/{id}")
    public TicketStopDTO stopTicket(@PathVariable String id) {
          return  ticketService.stopTicket(id);
    }

    @GetMapping("/tickets/{id}/charge")
    public BigDecimal checkCharge(@PathVariable String id) {
            return ticketService.checkCharge(id);
    }

}