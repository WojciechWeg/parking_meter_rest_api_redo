package com.wojtek.parkingmeter.Car;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface CarRepository extends CrudRepository<CarEntity, Long>{

    @Query("SELECT c.id FROM CarEntity c WHERE nr_plate = ?1")
    Integer findIdByNrPlate(String numerPlate);
}
