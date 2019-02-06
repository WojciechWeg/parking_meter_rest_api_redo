package com.wojtek.parkingmeter.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.OK)
public class TicketStoppedException extends RuntimeException{

    public TicketStoppedException(String exception) {
        super(exception);
    }
}
