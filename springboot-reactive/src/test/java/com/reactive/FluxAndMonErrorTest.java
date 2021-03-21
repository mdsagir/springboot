package com.reactive;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.Duration;
import java.util.Comparator;

public class FluxAndMonErrorTest {

    @Test
    void flux_error_handling_on_error_resume() {

        Flux<String> stringFlux = Flux.just("A", "B", "C", "D")
                .concatWith(Flux.error(new RuntimeException("Exception occurred")))
                .concatWith(Flux.just("E"))
                .onErrorResume(e -> {
                    System.out.println("Exception is " + e);
                    return Flux.just("default", "default1");
                });

        StepVerifier.create(stringFlux.log())
                .expectNext("A", "B", "C", "D","default", "default1")
                //.expectError(RuntimeException.class)
                .verifyComplete();
    }
    @Test
    void flux_error_handling_on_error_return() {

        Flux<String> stringFlux = Flux.just("A", "B", "C", "D")
                .concatWith(Flux.error(new RuntimeException("Exception occurred")))
                .concatWith(Flux.just("E"))
                .onErrorReturn("fallback");

        StepVerifier.create(stringFlux.log())
                .expectNext("A", "B", "C", "D","fallback")
                .verifyComplete();
    }
    @Test
    void flux_error_handling_on_error_map() {

        // map the RuntimeException to to CustomException
        Flux<String> stringFlux = Flux.just("A", "B", "C", "D")
                .concatWith(Flux.error(new RuntimeException("Exception occurred")))
                .concatWith(Flux.just("E"))
                .onErrorMap(e -> new CustomException(e));

        StepVerifier.create(stringFlux.log())
                .expectNext("A", "B", "C", "D")
                .expectError(CustomException.class)
                .verify();
    }
    @Test
    void flux_error_handling_on_error_with_retry() {

        // map the RuntimeException to to CustomException
        Flux<String> stringFlux = Flux.just("A", "B", "C", "D")
                .concatWith(Flux.error(new RuntimeException("Exception occurred")))
                .concatWith(Flux.just("E"))
                .onErrorMap(CustomException::new)
                .retry(2);


        StepVerifier.create(stringFlux.log())
                .expectNext("A", "B", "C", "D")
                .expectNext("A", "B", "C", "D")
                .expectNext("A", "B", "C", "D")
                .expectError(CustomException.class)
                .verify();
    }
}
