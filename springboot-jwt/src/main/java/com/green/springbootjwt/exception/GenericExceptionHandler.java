package com.green.springbootjwt.exception;

import com.green.springbootjwt.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GenericExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponse validationException(MethodArgumentNotValidException e) {

        final String errorMessage = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        ErrorResponse exceptionResponse = new ErrorResponse();
        exceptionResponse.setError("constraint_validator");
        exceptionResponse.setError_description(errorMessage);
        return exceptionResponse;
    }
}
