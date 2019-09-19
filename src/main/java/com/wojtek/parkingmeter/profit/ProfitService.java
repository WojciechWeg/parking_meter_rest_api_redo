package com.wojtek.parkingmeter.profit;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;


@Service
public class ProfitService {

    private final ProfitRepository profitRepository;

    public ProfitService(ProfitRepository profitRepository) {
        this.profitRepository = profitRepository;
    }

    public void addIncome(LocalDate date, BigDecimal income){

        ProfitEntity profitEntity = new ProfitEntity(date,income);

        profitRepository.save(profitEntity);
    }
}
