package com.wojtek.parkingmeter.Ticket;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.math.BigDecimal;


public interface TicketRepository extends CrudRepository<TicketEntity, Long> {

    @Query(value = "SELECT sum(charge) FROM TICKETS", nativeQuery = true)
    BigDecimal returnSumOfAllTickets();
}
