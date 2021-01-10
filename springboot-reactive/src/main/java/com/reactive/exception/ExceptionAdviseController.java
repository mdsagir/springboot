package com.reactive.exception;

import com.reactive.model.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import reactor.core.publisher.Mono;

@RestController
@RestControllerAdvice
public class ExceptionAdviseController {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(DataNotFoundException.class)
    public Mono<ErrorResponse> exceptionHandler(DataNotFoundException e){
       return Mono.just(new ErrorResponse(e.getMessage()));
    }

}
