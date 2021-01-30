package com.reactive;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class FluxTest {

    Flux<String> spring = Flux.just("Spring boot", "Spring cloud", "Spring security","Spring stream","Spring JPA");
    @Test
    void flux_limit_back_pressure() {

        spring.limitRate(2).log()
        .subscribe(s -> System.out.println("Element "+s));


    }

    @Test
    void zip_test() {

        Mono<String> mono1 = Mono.just("x");
        Flux<String> flux1 = Flux.just("{1}", "{2}", "{3}", "{4}");

        Flux.zip(mono1, flux1, (itemMono1, itemFlux1) ->  "-[" + itemFlux1 + itemMono1 + "]-").subscribe(System.out::println);

    }
}
