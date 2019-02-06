package com.wojtek.parkingmeter.helpers;

public class LaunchStatus {
    private final boolean isStarted;

    public LaunchStatus(boolean isStarted) {
        this.isStarted = isStarted;
    }

    public boolean isStarted() {
        return isStarted;
    }
}
