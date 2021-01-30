package com.reactive;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.util.ArrayList;
import java.util.List;

public class FluxAndMonoSubscribeTest {

    @Test
    void subscribe() {
        List<Integer> integers=new ArrayList<>();

        Flux<String> stringFlux = Flux
                .just("Spring", "Spring Boot", "Spring Cloud", "Spring Data", "Spring Stream")
                .log();

        stringFlux
                .parallel(4)
                .doOnNext(s -> integers.add( sleepTime(s)))
                .doOnComplete(()->{
                    System.out.println("Hello");
                    System.out.println("Number: "+integers);
                }).subscribe();


    }


    public int sleepTime(String s) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return s.length();
    }
}
