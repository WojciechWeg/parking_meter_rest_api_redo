package com.wojtek.parkingmeter.helpers.calcs;

import java.math.BigDecimal;

public interface Calc {

    BigDecimal calculate(Long duration);
}
