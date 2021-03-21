package com.reactive.project;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class FluxFilterWhenTest {

    @Test
    void filterWhenTest() {

        Flux<Integer> integerFlux = Flux.range(1, 5);

        Flux<String> stringFlux = integerFlux.collectList().flatMapMany(integers -> Flux.just("a", "b", "c"));

        stringFlux.subscribe(System.out::println);
    }

    public static Mono<String> getString() {
        System.out.println("Hello inside getString() methods");
        return Mono.just("Hello");
    }

    public static Mono<String> checkEvenNumber(int n) {
        System.out.println("hello");

        if (n % 2 == 0) {
            return getString();
        }
        return getString();
    }
}
