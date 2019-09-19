package com.wojtek.parkingmeter.helpers.calcs;

import com.wojtek.parkingmeter.ticket.TicketType;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Map;
import java.util.Optional;

@Component
public class ChargeCalculator {

    private final Map<String, Calc> calcs;

    public ChargeCalculator(Map<String, Calc> calcs) {
        this.calcs = calcs;
    }

    public BigDecimal charge(TicketType ticketType, Duration duration) {

        return Optional.ofNullable(calcs.get(ticketType.name())).orElseThrow(IllegalArgumentException::new)
                .calculate(Math.abs(duration.toHours())).setScale(2);
    }

}