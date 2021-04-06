package com.reactive;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.IntStream;

// then, thenMany thenEmpty
public class FluxThenMany {

    @Test
    void then() {

        Flux<Integer> integerFlux = Flux.create((FluxSink<Integer> fluxSink) -> {

            for (int i=1;i<=4;i++ ){
                fluxSink.next(i);
            }
        });

        integerFlux.subscribe(System.out::println);

    }

    public Flux<String> first() {
        System.out.println("First");
        return Flux.just("first").delayElements(Duration.ofSeconds(3));
    }

    public Flux<String> second() {
        System.out.println("Second");
        return Flux.just("second");
    }

    @Test
    public void testFirst() {

        Map<String,List<String>> list=new ConcurrentHashMap<>();
        //Flux<String> just = Flux.just("A", "B");
        Flux<String> just = Flux.empty();


        just.collectList().doOnNext(s -> {
            if (!s.isEmpty()) {
            System.out.println("Adding "+s);
            list.put("s",s);
            }
        }).subscribe();

        System.out.println("LIST size "+list.size());
        System.out.println("LIST size "+list.get("s"));

    }
}
