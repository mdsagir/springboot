package com.reactive;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.Duration;

public class FluxMergeTest {

    @Test
    void combine_two_fluxes() {

        Flux<String> flux1 = Flux.just("A", "B", "C");
        Flux<String> flux2 = Flux.just("D", "E", "E");
        Flux<String> mergeFlux = Flux.merge(flux1, flux2);

        StepVerifier.create(mergeFlux.log())
                .expectSubscription()
                .expectNext("A", "B", "C","D", "E", "E")
                .verifyComplete();
    }
    @Test
    void combine_two_fluxes_with_delay() {

        // merge not return order elements

        Flux<String> flux1 = Flux.just("A", "B", "C").delayElements(Duration.ofSeconds(1));
        Flux<String> flux2 = Flux.just("D", "E", "E").delayElements(Duration.ofSeconds(1));
        Flux<String> mergeFlux = Flux.merge(flux1, flux2);

        StepVerifier.create(mergeFlux.log())
                .expectSubscription()
                .expectNextCount(6)
                //.expectNext("A", "B", "C","D", "E", "E") // failed test case
                .verifyComplete();
    }
    @Test
    void combine_two_fluxes_with_concat() {

        // concat are wait to complete first flux then complete second

        Flux<String> flux1 = Flux.just("A", "B", "C").delayElements(Duration.ofSeconds(1));
        Flux<String> flux2 = Flux.just("D", "E", "E").delayElements(Duration.ofSeconds(1));
        Flux<String> concatFlux = Flux.concat(flux1, flux2);

        StepVerifier.create(concatFlux.log())
                .expectSubscription()
                .expectNext("A", "B", "C","D", "E", "E")
                .verifyComplete();
    }

    @Test
    void combine_two_fluxes_with_zip() {

        // concat are wait to complete first flux then complete second

        Flux<String> flux1 = Flux.just("A", "B", "C");//delayElements(Duration.ofSeconds(1));
        Flux<String> flux2 = Flux.just("D", "E", "E");//delayElements(Duration.ofSeconds(1));
        Flux<String> mergeFlux = Flux.zip(flux1, flux2,(f1,f2)->f1.concat(f2));

        StepVerifier.create(mergeFlux.log())
                .expectSubscription()
                .expectNext("AD", "BE", "CE")
                .verifyComplete();
    }
}
