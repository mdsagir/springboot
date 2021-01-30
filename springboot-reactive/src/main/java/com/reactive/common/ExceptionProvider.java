package com.reactive.common;

import com.reactive.document.User;
import com.reactive.exception.DataNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Mono;

@Slf4j
public class ExceptionProvider {

    public static <T> Mono<? extends T> handlerPublisher(String message) {
         message = "data not found for " + message;
        log.info(message);

       return Mono.defer(() -> Mono.error(new DataNotFoundException("message")));

        //return Mono.error(new DataNotFoundException(message));
    }

    /*public static <T> Publisher<? extends User> handlerPublisher(String message) {
        message = "data not found for " + message;
        log.info(message);
        return new DataNotFoundException(message);
    }*/

    public static <T> Mono<? extends T> handleException(String message) {

        log.info(message);
        return Mono.error(new DataNotFoundException(message));

        //return Mono.error(new DataNotFoundException(message));
    }
}
