package com.reactive;

import com.fasterxml.jackson.core.JsonProcessingException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

public class JSONIgnore {

    public static void main(String[] args) throws JsonProcessingException {

        Flux<String> just = Flux.empty();

        Long block = just.count().block();
        System.out.println(block);
        // System.out.println(just.count());
    }
}
