package com.wojtek.parkingmeter.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class TicketDoesNotExistException extends RuntimeException{

    public TicketDoesNotExistException(String exception) {
        super(exception);
    }
}
