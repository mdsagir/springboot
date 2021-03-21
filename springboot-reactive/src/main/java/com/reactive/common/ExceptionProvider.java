package com.reactive.common;

import com.reactive.exception.DataNotFoundException;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
public class ExceptionProvider {

    public static <T> Mono<? extends T> handleException(String message) {
        return Mono.defer(() -> {
            log.info(message);
            return Mono.error(new DataNotFoundException(message));
        });
    }

    public static <T> Mono<? extends T> somethingWentWrong(Throwable throwable) {
        return Mono.defer(() -> {
            log.error(throwable.toString());
            return Mono.error(new DataNotFoundException("ERROR_SOMETHING_WENT_WRONG"));
        });
    }
}
