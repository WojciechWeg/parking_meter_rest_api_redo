package com.wojtek.parkingmeter.repositories;

import com.wojtek.parkingmeter.model.TicketEntity;
import org.springframework.data.repository.CrudRepository;


public interface TicketRepository extends CrudRepository<TicketEntity, Long> {
}
