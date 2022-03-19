package com.reactive;

import reactor.core.publisher.Flux;

public class CombineLatestOperator {
    public static void main(String[] args) {
        int min = 1, max = 10;
        Flux<Integer> evenNumbers = Flux
                .range(min, max)
                .filter(x -> x % 2 == 0);

        Flux<Integer> oddNumbers = Flux
                .range(min, max)
                .filter(x -> x % 2 > 0);
        Flux<Integer> fluxOfIntegers = Flux.combineLatest(
                evenNumbers,
                oddNumbers,
                (a, b) -> {
                    System.out.println(a + " + " + b);
                    return Integer.sum(a, b);
                });
        fluxOfIntegers.subscribe(System.out::println);
    }
}
