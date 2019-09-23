package com.wojtek.parkingmeter.calc;

import com.wojtek.parkingmeter.helpers.calcs.DisabledCalc;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.springframework.test.util.AssertionErrors.assertEquals;

public class DisableCalcTest {

    DisabledCalc disabledCalc = new DisabledCalc();

    @Test
    public void lessThanHour(){
        assertEquals("Less than hour: ", BigDecimal.valueOf(0).setScale(2),disabledCalc.calculate((long) 0.5));
    }

    @Test
    public void moreThanHourAndLessThanTwoHours(){
        assertEquals("More than hour and less than two hours: ", BigDecimal.valueOf(2).setScale(2),disabledCalc.calculate((long) 1.25));
    }

    @Test
    public void moreThanTwoHours(){
        assertEquals("More than two hours ",BigDecimal.valueOf(4.40).setScale(2),disabledCalc.calculate((long) 2.25));
    }

    @Test
    public void moreThanThreeHours(){
        assertEquals("More than three hours ",BigDecimal.valueOf(7.28).setScale(2),disabledCalc.calculate((long) 3.25));
    }


}
