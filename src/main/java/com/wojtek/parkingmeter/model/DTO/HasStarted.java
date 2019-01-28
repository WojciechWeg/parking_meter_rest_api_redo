package com.wojtek.parkingmeter.model.DTO;

import com.wojtek.parkingmeter.helpers.enums.HasStartedEnum;

public class HasStarted {

    HasStartedEnum hasStarted;

    public HasStarted(HasStartedEnum hasStarted) {
        this.hasStarted = hasStarted;
    }

    public HasStartedEnum getHasStarted() {
        return hasStarted;
    }

    public void setHasStarted(HasStartedEnum hasStarted) {
        this.hasStarted = hasStarted;
    }
}
