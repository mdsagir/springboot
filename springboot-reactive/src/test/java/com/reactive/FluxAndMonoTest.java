package com.reactive;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class FluxAndMonoTest {

    @Test
    void fluxTest() {
        Flux<String> stringFluxWithError = Flux.just("Spring","Spring Boot","Spring Reactive","Spring Batch")
                //.concatWith(Flux.error(new RuntimeException("Occurred")));
                .concatWith(Flux.just("Just after")).log();

        stringFluxWithError.subscribe(System.out::println,
                System.err::println,
                ()->System.out.println("Completed"));

    }


    @Test
    void flux_test_without_errors() {
        Flux<String> stringFlux = Flux.just("Spring","Spring Boot","Spring Reactive","Spring Batch")
                .log();

        StepVerifier.create(stringFlux)
                .expectNext("Spring")
                .expectNext("Spring Boot")
                .expectNext("Spring Reactive")
                .expectNext("Spring Batch")
                .verifyComplete();


    }
    @Test
    void flux_test_with_errors() {
        Flux<String> stringFlux = Flux.just("Spring","Spring Boot","Spring Reactive","Spring Batch")
                .concatWith(Flux.error(new RuntimeException("Exception Occurred!")))
                .log();

        StepVerifier.create(stringFlux)
                .expectNext("Spring")
                .expectNext("Spring Boot")
                .expectNext("Spring Reactive")
                .expectNext("Spring Batch")
                //.expectError(RuntimeException.class)
                .expectErrorMessage("Exception Occurred!")
                .verify();

    }
    @Test
    void flux_test_elements_count() {
        Flux<String> stringFlux = Flux.just("Spring","Spring Boot","Spring Reactive","Spring Batch")
                .concatWith(Flux.error(new RuntimeException("Exception Occurred!")))
                .log();

        StepVerifier.create(stringFlux)
                .expectNextCount(4)
                //.expectError(RuntimeException.class)
                .expectErrorMessage("Exception Occurred!")
                .verify();

    }

    @Test
    void mono_test() {
        Mono<String> stringMono = Mono.just("Spring");
        StepVerifier.create(stringMono.log())
                .expectNext("Spring")
                .verifyComplete();
    }
    @Test
    void mono_test_errors() {

        StepVerifier.create(Mono.error(new RuntimeException("runtime")).log())
                .expectError(RuntimeException.class)
                .verify();
    }
}







