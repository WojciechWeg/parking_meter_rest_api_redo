package com.wojtek.parkingmeter.repositories;

import com.wojtek.parkingmeter.model.CarEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface CarRepository extends CrudRepository<CarEntity, Long>{

    @Query(value = "SELECT ID FROM CARS WHERE nr_plate = ?1", nativeQuery = true)
    Integer findIdByNrPlate(String numerPlate);
}
