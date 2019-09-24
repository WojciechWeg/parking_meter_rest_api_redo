package com.wojtek.parkingmeter.ticket;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface TicketRepository extends CrudRepository<TicketEntity, Long> {

    @Query(value = "SELECT c.id FROM tickets c WHERE cars_id IS NOT null AND car_number_plate = ?1 ",nativeQuery = true)
    Optional<Long> findByCarNumberPlateAndCarIdIsNull(String numberPlate);

}
