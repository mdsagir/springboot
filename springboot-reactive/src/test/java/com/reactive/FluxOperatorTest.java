package com.reactive;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

public class FluxOperatorTest {

    @Test
    void flux_handle() {

        Flux<Integer> handle = Flux.range(1, 10)
                .handle((integer, synchronousSink) -> {
                    System.out.println("Hello");
                    if (integer % 2 == 0)
                        synchronousSink.next(integer);
                }).cast(Integer.class);
        handle.subscribe(System.out::println);
    }

    @Test
    void flux_do_on_next() {

        // first donOnNext method called then subscribe
        // subscribe happens on bottom up
        Flux<Integer> integerFlux = Flux.range(1, 5);

        integerFlux.doOnNext(integer ->
                System.out.println("Integer "+integer))
                .subscribe(integer ->
                        System.out.println("Subscriber Integer "+integer));

    }

    @Test
    void flux_do_on_error() {

        Flux<Integer> integerFlux = Flux.range(1, 5)
                .concatWith(Flux.error(new RuntimeException("Exception occurred")));

        integerFlux
                .doOnNext(integer ->
                        System.out.println("doOnNext "+integer))
                .doOnError(throwable ->
                System.out.println("doOneError "+throwable.getMessage()))
                .subscribe();
    }

    @Test
    void flux_do_first_() {
        Flux<Integer> integerFlux = Flux.range(1, 5)
            .doFirst(() -> System.out.println("do First 1"))
                .doFirst(() -> System.out.println("do First 2"));
        integerFlux.subscribe();
    }

    @Test
    void flux_do_on_request_() {
        Flux<Integer> integerFlux = Flux.range(1, 5)
                .doOnRequest(request -> System.out.println("Request : "+request));
        integerFlux.subscribe(System.out::println);
    }

    @Test
    void flux_limit_rate() {
        Flux<Integer> integerFlux = Flux.range(1, 1000).log()
                //.limitRate(100); // 75 item are immited
                //.limitRate(100,100); // 75 item are immited in c
                .limitRate(100,0); // 75 item are immited in c
        integerFlux.subscribe(System.out::println);

    }



    @Test
    void flux_on_error_returns_() {

        Flux<Integer> integerFlux = Flux.range(1, 20)
                .map(i -> 10/(5-i))
                .onErrorReturn(-1); // return fallback

        integerFlux.subscribe(System.out::println);
    }

    @Test
    void flux_on_error_resume_() {

        Flux<Integer> integerFlux = Flux.range(1, 20)
                .map(i -> 10/(5-i))
                .onErrorResume(throwable -> {
                    System.out.println("Error "+throwable.toString());
                    return Mono.just(-1);
                });
        integerFlux.subscribe(System.out::println);
    }
    @Test
    void flux_on_error_continue_() {

        Flux<Integer> integerFlux = Flux.range(1, 20)
                .map(i -> 10/(5-i))
                .onErrorContinue((e, o) -> {

                });
        integerFlux.subscribe(System.out::println);
    }

    @Test
    void flux_timeout() {
        getIntegerFlux()
                .timeout(Duration.ofSeconds(2))
                .subscribe(System.out::println,
                        System.out::println);
    }

    private Flux<Integer> getIntegerFlux() {
        return Flux.range(1,10)
                .delayElements(Duration.ofSeconds(10));
    }

    @Test
    void flux_default_if_empty() {

        Flux<Integer> integerFlux = Flux.range(1,10);
        integerFlux.filter(integer  -> integer >10 )
                .defaultIfEmpty(-1).subscribe(System.out::println);
    }
    @Test
    void flux_switch_if_empty() {

        Flux<Integer> integerFlux = Flux.range(1,10);
        integerFlux.filter(integer  -> integer >10 )
                .switchIfEmpty(Mono.just(-1)).subscribe(System.out::println);
    }
}
