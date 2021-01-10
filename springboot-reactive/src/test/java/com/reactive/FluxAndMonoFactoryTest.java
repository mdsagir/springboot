package com.reactive;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Flow;
import java.util.function.Supplier;

public class FluxAndMonoFactoryTest {


    List<String> names= Arrays.asList("john","sara","mathews");

    @Test
    void flux_using_iterable() {

        Flux<String> stringFlux = Flux.fromIterable(names);

        StepVerifier.create(stringFlux.log())
                .expectNext("john","sara","mathews")
                .verifyComplete();
    }
    @Test
    void flux_using_array() {

        String[] stringArray=new String[]{"john","sara","mathews"};
        Flux<String> stringFlux = Flux.fromArray(stringArray);

        StepVerifier.create(stringFlux.log())
                .expectNext("john","sara","mathews")
                .verifyComplete();
    }
    @Test
    void flux_using_stream() {

        Flux<String> stringFlux = Flux.fromStream(names.stream());

        StepVerifier.create(stringFlux.log())
                .expectNext("john","sara","mathews")
                .verifyComplete();
    }
    @Test
    void flux_using_range() {

        Flux<Integer> range = Flux.range(1, 4);

        StepVerifier.create(range.log())
                .expectNext(1,2,3,4)
                .verifyComplete();
    }
    @Test
    void mono_just_and_empty() {

        Mono<String> objectMono = Mono.justOrEmpty(null);

        StepVerifier.create(objectMono.log())
                .verifyComplete();
    }
    @Test
    void mono_from_supplier() {

        Supplier<String>  stringSupplier= ()-> "Hello";
        Mono<String> stringMono = Mono.fromSupplier(stringSupplier);

        StepVerifier.create(stringMono.log())
                .expectNext("Hello")
                .verifyComplete();
    }
}
