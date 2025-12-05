package com.liaw.dev.Conference.exceptions;

import com.liaw.dev.Conference.exceptions.conference.ConferenceExistException;
import com.liaw.dev.Conference.exceptions.conference.ConferenceNotFoundException;
import com.liaw.dev.Conference.exceptions.payment.PaymentNotFoundException;
import com.liaw.dev.Conference.exceptions.user.UserExistException;
import com.liaw.dev.Conference.exceptions.user.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class HandleException {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(UserNotFoundException.class)
    public ErrorMessage handleUserNotFoundException(UserNotFoundException e){
        return new ErrorMessage(
                HttpStatus.NOT_FOUND.value(),
                e.getMessage()
        );
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ConferenceNotFoundException.class)
    public ErrorMessage handleConferenceNotFoundException(ConferenceNotFoundException e){
        return new ErrorMessage(
                HttpStatus.NOT_FOUND.value(),
                e.getMessage()
        );
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(UserExistException.class)
    public ErrorMessage handleUserExistException(UserExistException e){
        return new ErrorMessage(
                HttpStatus.CONFLICT.value(),
                e.getMessage()
        );
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(ConferenceExistException.class)
    public ErrorMessage handleConferenceExistException(ConferenceExistException e){
        return new ErrorMessage(
                HttpStatus.CONTINUE.value(),
                e.getMessage()
        );
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(PaymentNotFoundException.class)
    public ErrorMessage handlePaymentNotFoundException(PaymentNotFoundException e){
        return new ErrorMessage(
                HttpStatus.NOT_FOUND.value(),
                e.getMessage()
        );
    }

}
