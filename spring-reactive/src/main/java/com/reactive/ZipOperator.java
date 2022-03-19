package com.reactive;

import reactor.core.publisher.Flux;

public class ZipOperator {
    public static void main(String[] args) {

        Flux<Integer> evenNumbers = Flux.just(1, 2, 3, 4, 3, 5, 5, 5);
        Flux<Integer> oddNumbers = Flux.just(1, 2, 3, 4, 5, 6);
        final Flux<Integer> integerFlux = Flux.zip(evenNumbers, oddNumbers, (a, b) -> {
            System.out.println(a + " + " + b);
            return Integer.sum(a, b);
        });
        integerFlux.subscribe(System.out::println);
    }
}
