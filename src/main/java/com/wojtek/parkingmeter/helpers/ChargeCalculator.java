package com.wojtek.parkingmeter.helpers;

import com.wojtek.parkingmeter.Ticket.TicketType;

import java.math.BigDecimal;
import java.time.Duration;

public class ChargeCalculator {

    public static BigDecimal charge(TicketType ticketType, Duration duration) {

        if (TicketType.REGULAR == ticketType)
            return countRegularTicketType(Math.abs(duration.toHours()));

        if (TicketType.DISABLED == ticketType)
            return countDisabledTicketType(Math.abs(duration.toHours()));

        return BigDecimal.valueOf(-1.0);
    }

    public static BigDecimal countRegularTicketType(Long duration) {

        double charge = 0.0;

        if (duration <= 1)
            charge = charge + 1;
        if (duration <= 2 && duration >= 1)
            charge = charge + 2;
        if (duration >= 2) {
            charge = 3;
            double lastPrice = 2;

            for (int i = 2; i <= duration; i++) {
                lastPrice = lastPrice * 1.5;
                charge = charge + lastPrice;
            }
        }

        return BigDecimal.valueOf(charge).setScale(2);
    }

    public static BigDecimal countDisabledTicketType(Long duration) {

        double charge = 0.0;

        if (duration <= 2 && duration >= 1)
            charge = charge + 2;
        if (duration >= 2) {
            charge = 2;
            double lastPrice = 2;

            for (int i = 2; i <= duration; i++) {
                lastPrice = lastPrice * 1.2;
                charge = charge + lastPrice;


            }
        }
        return BigDecimal.valueOf(charge).setScale(2);
    }

}