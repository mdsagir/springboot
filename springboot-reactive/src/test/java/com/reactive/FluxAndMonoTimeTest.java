package com.reactive;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.Duration;

public class FluxAndMonoTimeTest {

    @Test
    void flux_time_interval() throws InterruptedException {

        Flux<Long> fluxInterval = Flux.interval(Duration.ofMillis(100)).log();

        fluxInterval.subscribe(e-> System.out.println("Value is : "+e));

        Thread.sleep(3000);
    }
    @Test
    void flux_time_interval_with_take() throws InterruptedException {

        Flux<Long> fluxInterval = Flux.interval(Duration.ofMillis(100))
                .take(3).log();

        StepVerifier.create(fluxInterval)
                .expectSubscription()
                .expectNextCount(3)
                .verifyComplete();
    }
}
