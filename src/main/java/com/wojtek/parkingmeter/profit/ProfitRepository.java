package com.wojtek.parkingmeter.profit;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface ProfitRepository extends CrudRepository<ProfitEntity,Long> {

    @Query(value = "SELECT sum(income) from profits where date = ?1",nativeQuery = true)
    BigDecimal sumIncomeByDate(LocalDate localDate);


}
