package com.reactive;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;

import java.util.Random;

/**
 * FLux create methods allow us programmatically emmit the values in flux.
 * <p>
 * Flux.create(Consumer< FluxSink< ? extends Object > > object)
 */
public class FluxCreateTest {

    @Test
    void flux_create() {

        Flux<String> stringFlux = Flux.create(fluxSink -> {
            fluxSink.next("Java");
            fluxSink.next("Spring");
            fluxSink.complete();
        }).cast(String.class);

        stringFlux.subscribe(System.out::println);
    }

    @Test
    void flux_create_complete() {

        Flux<String> stringFlux = Flux.create(fluxSink -> {

            for (int i = 0; i < 10; i++) {
                if (i == 8) {
                    fluxSink.complete();
                }
                fluxSink.next(Faker.instance().name().firstName());
            }
        }).cast(String.class);

        stringFlux.subscribe(System.out::println);
    }

    @Test
    void flux_complete_take() {

        // take cancel the subscription after get the 3 elements emits
        Flux<String> stringFlux = Flux.create(fluxSink -> {

            for (int i = 0; i < 10; i++) {
                System.out.println("generating name : " + i);
                if (i == 8) {
                    fluxSink.complete();
                }
                fluxSink.next(Faker.instance().name().firstName());
            }
        }).cast(String.class);
        stringFlux.take(3).subscribe(System.out::println);
    }

    @Test
    void flux_generate_() {

        Flux<Object> hellFlux = Flux.generate(synchronousSink -> {
            System.out.println("Emitting");
            synchronousSink.next(Faker.instance().country().name());
            synchronousSink.complete();
        }).take(2);

        hellFlux.subscribe(System.out::println);
    }

    @Test
    void flux_generate_loop() {

        Flux<Object> hellFlux = Flux.generate(synchronousSink -> {
            String country = Faker.instance().country().name();
            synchronousSink.next(country);
            if (country.equalsIgnoreCase("canada"))
                synchronousSink.complete();
        });
        hellFlux.subscribe(System.out::println);
    }

    @Test
    void flux_generate_with_() {


        Flux<Integer> numbering = Flux.generate(synchronousSink -> {
            Random random = new Random();
            int i = random.nextInt(10 - 1 + 1) + 1;
            synchronousSink.next(i);
        }).cast(Integer.class);

        numbering.subscribe(System.out::println);
    }

    @Test
    void flux_generate_with_state() {

        Flux<Integer> integerFlux = Flux.generate(
                () -> 1,
                (state, sink) -> {

                    if (state>5) {
                        sink.complete();
                    }
                    sink.next(state);
                    state++;
                    return state;
                }
        ).cast(Integer.class);

        integerFlux.subscribe(System.out::println);
    }

    @Test
    void flux_push_() {
        // push is not thread safe
        Flux<Integer> integerFlux = Flux.push(fluxSink -> {
            for (int i = 0; i < 10; i++) {
                fluxSink.next(i);
            }
        }).cast(Integer.class);
        integerFlux.subscribe(System.out::println);
    }
}
