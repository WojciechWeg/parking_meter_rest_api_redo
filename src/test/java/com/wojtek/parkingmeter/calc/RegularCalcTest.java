package com.wojtek.parkingmeter.calc;

import com.wojtek.parkingmeter.helpers.calcs.RegularCalc;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.springframework.test.util.AssertionErrors.assertEquals;


public class RegularCalcTest {

    RegularCalc regularCalc = new RegularCalc();

    @Test
    public void lessThanHour(){
        assertEquals("Less than hour: ", BigDecimal.valueOf(1.00).setScale(2),regularCalc.calculate((long) 0.5));
    }

    @Test
    public void moreThanHourAndLessThanTwoHours(){
        assertEquals("More than hour and less than two hours: ",BigDecimal.valueOf(3.00).setScale(2),regularCalc.calculate((long) 1.25));
    }

    @Test
    public void moreThanTwoHours(){
        assertEquals("More than two hours ",BigDecimal.valueOf(6.00).setScale(2),regularCalc.calculate((long) 2.25));
    }

    @Test
    public void moreThanThreeHours(){
        assertEquals("More than three hours ",BigDecimal.valueOf(10.5).setScale(2),regularCalc.calculate((long) 3.25));
    }

}
