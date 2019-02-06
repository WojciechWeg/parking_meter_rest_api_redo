package com.wojtek.parkingmeter.helpers.calcs;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component("DISABLED")
public class DisabledCalc implements Calc {

    @Override
    public BigDecimal calculate(Long hours) {
        double charge = 0.0;

        if (hours <= 2 && hours >= 1)
            charge = charge + 2;
        if (hours >= 2) {
            charge = 2;
            double lastPrice = 2;

            for (int i = 2; i <= hours; i++) {
                lastPrice = lastPrice * 1.2;
                charge = charge + lastPrice;


            }
        }
        return BigDecimal.valueOf(charge).setScale(2);
    }
}
