package com.wojtek.parkingmeter.helpers;

import com.wojtek.parkingmeter.helpers.enums.TicketType;
import com.wojtek.parkingmeter.model.TicketEntity;

import org.junit.Test;

import static org.junit.Assert.*;

import java.time.LocalDateTime;

public class ChargeCalculatorTest {


    @Test
    public void RegularLessThanOneHour() {

        //given
        TicketEntity ticketEntity1 = new TicketEntity(TicketType.REGULAR, LocalDateTime.of(2019, 1, 13, 19, 00, 00, 00), LocalDateTime.of(2019, 1, 13, 19, 30, 00, 00));

        //when
        ticketEntity1.setCharge(ChargeCalculator.charge(ticketEntity1.getTicketType(), ticketEntity1.getDuration()));

        //then
        assertEquals(1, ticketEntity1.getCharge(), 0.001);
    }

    @Test
    public void RegularLessThanTwoHours() {

        //given
        TicketEntity ticketEntity2 = new TicketEntity(TicketType.REGULAR, LocalDateTime.of(2019, 1, 13, 19, 00, 00, 00), LocalDateTime.of(2019, 1, 13, 20, 01, 00, 00));


        //when
        ticketEntity2.setCharge(ChargeCalculator.charge(ticketEntity2.getTicketType(), ticketEntity2.getDuration()));

        //then
        assertEquals(3, ticketEntity2.getCharge(), 0.001);
    }

    @Test
    public void RegularLessThanThreeHours() {

        //given
        TicketEntity ticketEntity3 = new TicketEntity(TicketType.REGULAR, LocalDateTime.of(2019, 1, 13, 19, 00, 00, 00), LocalDateTime.of(2019, 1, 13, 21, 01, 00, 00));


        //when
        ticketEntity3.setCharge(ChargeCalculator.charge(ticketEntity3.getTicketType(), ticketEntity3.getDuration()));

        //then
        assertEquals(6, ticketEntity3.getCharge(), 0.001);
    }


    @Test
    public void RegularLessThanFourHours() {

        //given
        TicketEntity ticketEntity4 = new TicketEntity(TicketType.REGULAR, LocalDateTime.of(2019, 1, 13, 19, 00, 00, 00), LocalDateTime.of(2019, 1, 13, 22, 01, 00, 00));

        //when
        ticketEntity4.setCharge(ChargeCalculator.charge(ticketEntity4.getTicketType(), ticketEntity4.getDuration()));


        //then
        assertEquals(10.5, ticketEntity4.getCharge(), 0.001);
    }

    @Test
    public void DisabledLessThanOneHours() {

        //given
        TicketEntity ticketEntity5 = new TicketEntity(TicketType.DISABLED, LocalDateTime.of(2019, 1, 13, 19, 00, 00, 00), LocalDateTime.of(2019, 1, 13, 19, 30, 00, 00));

        //when
        ticketEntity5.setCharge(ChargeCalculator.charge(ticketEntity5.getTicketType(), ticketEntity5.getDuration()));

        //then
        assertEquals(0.0, ticketEntity5.getCharge(), 0.001);
    }

    @Test
    public void DisabledLessThanTwoHours() {

        //given
        TicketEntity ticketEntity6 = new TicketEntity(TicketType.DISABLED, LocalDateTime.of(2019, 1, 13, 19, 00, 00, 00), LocalDateTime.of(2019, 1, 13, 20, 01, 00, 00));

        //when
        ticketEntity6.setCharge(ChargeCalculator.charge(ticketEntity6.getTicketType(), ticketEntity6.getDuration()));
        //then
        assertEquals(2.0, ticketEntity6.getCharge(), 0.001);

    }

    @Test
    public void DisabledLessThanThreeHours() {

        //given
        TicketEntity ticketEntity7 = new TicketEntity(TicketType.DISABLED, LocalDateTime.of(2019, 1, 13, 19, 00, 00, 00), LocalDateTime.of(2019, 1, 13, 21, 01, 00, 00));

        //when
        ticketEntity7.setCharge(ChargeCalculator.charge(ticketEntity7.getTicketType(), ticketEntity7.getDuration()));
        //then
        assertEquals(4.4, ticketEntity7.getCharge(), 0.001);

    }

    @Test
    public void DisabledLessThanFourHours() {

        //given
        TicketEntity ticketEntity8 = new TicketEntity(TicketType.DISABLED, LocalDateTime.of(2019, 1, 13, 19, 00, 00, 00), LocalDateTime.of(2019, 1, 13, 22, 01, 00, 00));

        //when
        ticketEntity8.setCharge(ChargeCalculator.charge(ticketEntity8.getTicketType(), ticketEntity8.getDuration()));

        //then
        assertEquals(7.28, ticketEntity8.getCharge(), 0.001);

    }

}
