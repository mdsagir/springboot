package com.reactive;

import reactor.core.publisher.Flux;

public class HandleOperator {

    public static void main(String[] args) {

        final Flux<Integer> range = Flux.range(1, 5);
        final Flux<Integer> even = range.handle((integer, synchronousSink) -> {
            if (integer % 2 == 0) {
                synchronousSink.next(integer);
            }
        }).cast(Integer.class);
        even.subscribe(System.out::println);

        final Flux<String> stringFlux = range.handle((integer, synchronousSink) -> {
            if (integer % 2 == 0) {
                synchronousSink.next(integer + "SUM");
            }
        }).cast(String.class);
        stringFlux.subscribe(System.out::println);
    }
}
