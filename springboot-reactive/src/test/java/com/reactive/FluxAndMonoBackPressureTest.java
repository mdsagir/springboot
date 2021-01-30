package com.reactive;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;

public class FluxAndMonoBackPressureTest {

    @Test
    void back_pressure_request_test() {

        Flux<Integer> integerFlux = Flux.range(1, 10)
                .log();
        integerFlux.subscribe(
                e -> System.out.println("Element " + e),
                e -> System.out.println("Error " + e),
                () -> System.out.println("Complete"),
                s -> s.request(2));

    }
    @Test
    void back_pressure_cancel_test() {

        Flux<Integer> integerFlux = Flux.range(1, 10)
                .log();
        integerFlux.subscribe(
                e -> System.out.println("Element " + e),
                e -> System.out.println("Error " + e),
                () -> System.out.println("Complete"),
                s -> s.cancel());
    }
    @Test
    void custom_back_pressure_test() {

        Flux<Integer> integerFlux = Flux.range(1, 10)
                .log();

        integerFlux.subscribe(new BaseSubscriber<>() {
            @Override
            protected void hookOnNext(Integer value) {
                request(1);
                System.out.println("Value: " + value);
                if (value == 4) {
                    cancel();
                }
            }
        });
    }

    @Test
    void back_pressure_test() {

        Flux<Integer> integerFlux = Flux.range(1, 10);


    }
}
