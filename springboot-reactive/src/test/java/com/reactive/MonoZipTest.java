package com.reactive;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public class MonoZipTest {


    @Test
    void zip_test_with_mono() {

        Flux<String> fruits = Flux.just("Apple", "Orange");
        Flux<String> animals = Flux.just("Cow", "Dog");
        Flux<String> food = Flux.just("water", "meals");

        Flux<Mono<List<String>>> zip = Flux.zip(fruits.collectList(), animals.collectList(), (f, a) -> {
            f.addAll(a);
            return Mono.just(f);
        });

        Flux<String> stringFlux = Flux.zip(fruits.collectList(), animals.collectList(),food.collectList()).flatMap(tuple -> {
            List<String> t1 = tuple.getT1();
            List<String> t2 = tuple.getT2();
            List<String> t3 = tuple.getT3();
            t1.addAll(t2);
            t1.addAll(t3);
            return Mono.just(t1);
        }).flatMap(Flux::fromIterable);

        stringFlux.subscribe(System.out::println);

    }
}
