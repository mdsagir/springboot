package com.mintgenie.mintgeniecore.utility;

import com.mintgenie.mintgeniecore.commons.exception.DataNotFoundException;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
public class ExceptionProvider {


    public static <T> Mono<? extends T> dataNotFound(Number message) {
        return Mono.defer(() -> {
            String errorMessage = "Data not found for this id"+ message;
            log.error(errorMessage);
            return Mono.error(new DataNotFoundException(errorMessage));
        });
    }

    public static <T> Mono<? extends T> dataNotFound(String  message) {
        return Mono.defer(() -> {
            String errorMessage = "Data not found for "+ message;
            log.error(errorMessage);
            return Mono.error(new DataNotFoundException(errorMessage));
        });
    }
    public static <T> Mono<? extends T> somethingWentWrong(Throwable throwable) {
        return Mono.defer(() -> {
            log.error(throwable.toString());
            return Mono.error(new DataNotFoundException("Something went wrong"));
        });
    }
}
