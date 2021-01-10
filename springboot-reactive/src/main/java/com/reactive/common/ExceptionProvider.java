package com.reactive.common;

import com.reactive.exception.DataNotFoundException;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
public class ExceptionProvider {

    public static <T> Mono<? extends T> handlerPublisher(String message) {
        message = "data not found for " + message;
        log.info(message);
        return Mono.error(new DataNotFoundException(message));
    }
}
