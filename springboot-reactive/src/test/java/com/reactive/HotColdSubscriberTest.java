package com.reactive;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.stream.Stream;

public class HotColdSubscriberTest {

    @Test
    void cold_subscriber_test() throws InterruptedException {

        Flux<String> movieFlux = Flux.fromStream(this::getMovie)
                .delayElements(Duration.ofSeconds(1));

        movieFlux.subscribe(s -> {
            System.out.println("First Subscriber "+s);
        });

        Thread.sleep(4000);
        movieFlux.subscribe(s -> {
            System.out.println("Second Subscriber "+s);
        });
        Thread.sleep(10000);

    }
    @Test
    void hot_subscriber_share_test() throws InterruptedException {

        // while joining the publisher if first publisher are not completed then second publisher are joining
        // the same as publisher

        Flux<String> movieFlux = Flux.fromStream(this::getMovie)
                .delayElements(Duration.ofSeconds(1))
                .share();

        movieFlux.subscribe(s -> {
            System.out.println("First Subscriber "+s);
        });

        Thread.sleep(3000);
        movieFlux.subscribe(s -> {
            System.out.println("Second Subscriber "+s);
        });
        Thread.sleep(10000);

    }
    @Test
    void hot_subscriber_publish_test() throws InterruptedException {

        //  .publish().refCount(1); is equal to share
        // refCount(int num) is allow unless to join n number of publisher

        Flux<String> movieFlux = Flux.fromStream(this::getMovie)
                .delayElements(Duration.ofSeconds(1))
                .publish().refCount(2);

        movieFlux.subscribe(s -> {
            System.out.println("First Subscriber "+s);
        });

        Thread.sleep(3000);
        movieFlux.subscribe(s -> {
            System.out.println("Second Subscriber "+s);
        });
        Thread.sleep(3000);
        movieFlux.subscribe(s -> {
            System.out.println("Third Subscriber "+s);
        });
        Thread.sleep(10000);

    }
    @Test
    void hot_subscriber_auto_connect_test() throws InterruptedException {

        // .publish().refCount(1); is equal to share
        // autoConnect(int n) is allow unless to join n number of publisher
        // after complete the subscriber  n publisher will not be emit the element

        Flux<String> movieFlux = Flux.fromStream(this::getMovie)
                .delayElements(Duration.ofSeconds(1))
                .publish().autoConnect(2); // autoConnect(0) even no need to join any subscriber

        movieFlux.subscribe(s -> {
            System.out.println("First Subscriber "+s);
        });

        Thread.sleep(7000);
        movieFlux.subscribe(s -> {
            System.out.println("Second Subscriber "+s);
        });
        Thread.sleep(10000);

    }
    @Test
    void hot_subscriber_auto_cache_test() throws InterruptedException {

        // .publish().refCount(1); is equal to share
        // autoConnect(int n) is allow unless to join n number of publisher
        // after complete the subscriber  n publisher will not be emit the element

        Flux<String> movieFlux = Flux.fromStream(this::getMovie)
                .delayElements(Duration.ofSeconds(1))
                .cache(); // autoConnect(0) even no need to join any subscriber

        movieFlux.subscribe(s -> {
            System.out.println("First Subscriber "+s);
        });

        Thread.sleep(7000);
        movieFlux.subscribe(s -> {
            System.out.println("Second Subscriber "+s);
        });
        Thread.sleep(10000);

    }
    public Stream<String> getMovie() {
        System.out.println("Hello");
        return Stream.of(
                "Scene 1",
                "Scene 2",
                "Scene 3",
                "Scene 4",
                "Scene 5",
                "Scene 6"
        );
    }
}
