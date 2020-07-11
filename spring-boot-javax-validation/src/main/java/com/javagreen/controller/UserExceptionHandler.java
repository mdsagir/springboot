package com.javagreen.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@ControllerAdvice
@RestController
public class UserExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ExceptionResponse validationException(MethodArgumentNotValidException e) {

        ExceptionResponse exceptionResponse = new ExceptionResponse();
        exceptionResponse.setDate(new Date());
        final String errorMessage = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();

        exceptionResponse.setMessage(errorMessage);
        return exceptionResponse;
    }
    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(UserExistException.class)
    public ExceptionResponse userExists(UserExistException e) {

        ExceptionResponse exceptionResponse = new ExceptionResponse();
        exceptionResponse.setDate(new Date());
        final String errorMessage = e.getMessage();

        exceptionResponse.setMessage(errorMessage);
        return exceptionResponse;
    }
}
