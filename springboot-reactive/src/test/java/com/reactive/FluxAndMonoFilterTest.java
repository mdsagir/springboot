package com.reactive;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;

public class FluxAndMonoFilterTest {

    List<String> names= Arrays.asList("john","sara","mathews");

    @Test
    void flux_filter() {

        Flux<String> stringFlux = Flux.fromIterable(names)
                .filter(s -> s.length() < 5);

        StepVerifier.create(stringFlux.log())
                .expectNext("john","sara")
                .verifyComplete();
    }
    @Test
    void flux_map() {

        Flux<Integer> stringFlux = Flux.fromIterable(names)
                .map(String::length);

        StepVerifier.create(stringFlux.log())
                .expectNext(4,4,7)
                .verifyComplete();
    }
    @Test
    void flux_flatMap() {

        Flux<Integer> stringFlux = Flux.fromIterable(names)
                .flatMap(s -> Mono.just(s.length()));

        StepVerifier.create(stringFlux.log())
                .expectNext(4,4,7)
                .verifyComplete();
    }

}
