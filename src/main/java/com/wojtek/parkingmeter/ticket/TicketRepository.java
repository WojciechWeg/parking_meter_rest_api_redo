package com.wojtek.parkingmeter.ticket;

import org.springframework.data.repository.CrudRepository;

public interface TicketRepository extends CrudRepository<TicketEntity, Long> {

}
