package com.wojtek.parkingmeter.profit;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDate;

@RestController
public class ProfitController {

    private final ProfitRepository profitRepository;

    public ProfitController(ProfitRepository profitRepository) {
        this.profitRepository = profitRepository;
    }

    @GetMapping("/profit")
    public BigDecimal getProfit(@RequestParam("date")
                                    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate localDate){

        return profitRepository.sumIncomeByDate(localDate);
    }
}
