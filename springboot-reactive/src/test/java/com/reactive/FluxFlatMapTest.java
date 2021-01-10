package com.reactive;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;

public class FluxFlatMapTest {

    List<String> names = Arrays.asList("A", "B", "C", "D", "E", "F");

    @Test
    void flux_flat_map() {

        Flux<String> stringFlux = Flux.fromIterable(names)
                .flatMap(s -> Flux.fromIterable(convertList(s)));

        StepVerifier.create(stringFlux.log())
                .expectNextCount(12)
                .verifyComplete();

    }

    @Test
    void flux_flat_map_using_parallel() {

        Flux<String> stringFlux = Flux.fromIterable(names)
                .window(2) // Flux<Flux<String>> (A,B) (C,D) ..
                .flatMap((s) ->
                        s.map(this::convertList).subscribeOn(Schedulers.parallel())
                                .flatMap(Flux::fromIterable)
                );

        StepVerifier.create(stringFlux.log())
                .expectNextCount(12)
                .verifyComplete();

    }

    @Test
    void flux_flat_map_using_parallel_ordered() {

        Flux<String> stringFlux = Flux.fromIterable(names)
                .window(2) // Flux<Flux<String>> (A,B) (C,D) ..
                /*.concatMap((s) ->
                        s.map(this::convertList).subscribeOn(Schedulers.parallel())
                                .flatMap(Flux::fromIterable)*/
                .flatMapSequential((s) ->
                        s.map(this::convertList).subscribeOn(Schedulers.parallel())
                                .flatMap(Flux::fromIterable)
                );

        StepVerifier.create(stringFlux.log())
                .expectNextCount(12)
                .verifyComplete();

    }

    private List<String> convertList(String s) {

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return Arrays.asList(s, "newElement");
    }
}
