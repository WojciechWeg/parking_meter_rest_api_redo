package com.wojtek.parkingmeter.repositories;

import com.wojtek.parkingmeter.model.TicketEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.math.BigDecimal;


public interface TicketRepository extends CrudRepository<TicketEntity, Long> {

    @Query(value = "SELECT ID FROM TICKETS WHERE CAR_ID = ?1", nativeQuery = true)
    Integer findTicketIDByCarID(Integer carID);


    @Query(value = "SELECT sum(charge) FROM TICKETS", nativeQuery = true)
    BigDecimal returnSumOfAllTickets();
}
