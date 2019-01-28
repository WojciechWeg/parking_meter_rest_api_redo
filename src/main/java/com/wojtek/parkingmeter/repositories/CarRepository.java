package com.wojtek.parkingmeter.repositories;

import com.wojtek.parkingmeter.model.CarEntity;
import org.springframework.data.repository.CrudRepository;

public interface CarRepository extends CrudRepository<CarEntity, Long> {
}
