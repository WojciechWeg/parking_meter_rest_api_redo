package com.wojtek.parkingmeter.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.OK)
public class TicketInputDataExceptionOK extends RuntimeException {

    public TicketInputDataExceptionOK() {
        super();
    }
}
