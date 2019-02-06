package com.wojtek.parkingmeter.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CarAlreadyStartedException extends RuntimeException {

    public CarAlreadyStartedException() {
        super();
    }
}
