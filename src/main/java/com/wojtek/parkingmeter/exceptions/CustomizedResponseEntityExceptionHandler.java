package com.wojtek.parkingmeter.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice
@RestController
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(InvalidIDNumberException.class)
    public final ResponseEntity<ErrorDetails> handleInvalidIDNumber(InvalidIDNumberException ex, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage());
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TicketDoesNotExistException.class)
    public final ResponseEntity<ErrorDetails> handleTicketDoesNotExist(TicketDoesNotExistException ex, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage());
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(TicketStoppedException.class)
    public final ResponseEntity<ErrorDetails> handleTicketStopped(TicketStoppedException ex, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage());
        return new ResponseEntity<>(errorDetails, HttpStatus.OK);
    }

    @ExceptionHandler(TicketIncorrectInputDataException.class)
    public final ResponseEntity<ErrorDetails> handleTicketIncorrectInputData(TicketIncorrectInputDataException ex, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage());
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CarAlreadyStartedException.class)
    public final ResponseEntity<ErrorDetails> handleCarDoesNotExist(CarAlreadyStartedException ex, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage());
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidNumberPlateException.class)
    public final ResponseEntity<ErrorDetails> handleCarDoesNotExist(InvalidNumberPlateException ex, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage());
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

}