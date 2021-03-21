package com.reactive;


import com.github.javafaker.Faker;
import org.junit.jupiter.api.Test;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;

/**
 * 1.Flux.just()
 * 2.Flux.fromArray()
 * 3.FLux.fromIterable()
 * 4.Flux.fromStream()
 * 5.Flux.range(1,5)
 * 6.Flux.interval(Duration.ofSeconds(1))
 * 7.Flux.create
 * 8.Flux.generate()
 * 9.Flux.from(Publisher p)
 */
public class FluxTest {

    @Test
    void flux_just_() {

        Flux<String> apple = Flux.just("Apple", "Orange", "Mango");
        apple.subscribe(System.out::println);
    }

    @Test
    void flux_from_array_() {
        Integer[] i = {1, 2, 3, 4, 5};

        Flux<Integer> integerFlux = Flux.fromArray(i);
        integerFlux.subscribe(System.out::println);

    }

    @Test
    void flux_from_stream() {

        List<Integer> array = Arrays.asList(1, 2, 3);
        Stream<Integer> stream = array.stream();
        Flux<Integer> integerFlux = Flux.fromStream(stream);
        integerFlux.subscribe(System.out::println);
        // integerFlux.subscribe(System.out::println);
        //can not subscribe again
    }

    @Test
    void flux_from_stream_subscribe_again() {

        List<Integer> array = Arrays.asList(1, 2, 3);
        Stream<Integer> stream = array.stream();
        Flux<Integer> integerFlux = Flux.fromStream(array::stream);
        integerFlux.subscribe(System.out::println);
        integerFlux.subscribe(System.out::println);
        //can not subscribe again
    }

    @Test
    void flux_from_iterable_() {
        List<Integer> integers = Arrays.asList(1, 2, 3, 4);
        Flux<Integer> integerFlux = Flux.fromIterable(integers);
        integerFlux.subscribe(System.out::println);
    }

    @Test
    void flux_range() {

        Flux<Integer> range = Flux.range(1, 5);
        range.subscribe(System.out::println);

        Flux<String> names = Flux.range(1, 5)
                .map(integer -> Faker.instance().name().firstName());
        names.subscribe(System.out::println);
    }

    @Test
    void flux_subscribe_with_() {

        Flux.range(1, 5)
                .subscribeWith(new Subscriber<Integer>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        System.out.println("onSubscribe");
                        s.request(5);
                    }

                    @Override
                    public void onNext(Integer integer) {
                        System.out.println("onNext " + integer);
                    }

                    @Override
                    public void onError(Throwable t) {
                        System.out.println("onError");
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("onComplete");
                    }
                });
    }

    @Test
    void flux_and_list_() {

        /*List<String> list = createList(5);
        list.forEach(System.out::println);*/

        Flux<String> flux = createFlux(5);
        flux.subscribe(System.out::println);
    }

    @Test
    void flux_interval() throws InterruptedException {
        // running in different threads
        Flux.interval(Duration.ofSeconds(1)).log()
                .subscribe(System.out::println);
        Thread.sleep(2000);
    }

    @Test
    void flux_from_mono() {

        Mono<String> stringMono = Mono.just("hello");
        Flux<String> stringFlux = Flux.from(stringMono);
        stringFlux.subscribe(System.out::println);
    }

    public Flux<String> createFlux(int count) {
        return Flux.range(0,count)
                .map(integer -> getName());
    }

    @Test
    void mono_from_flux() {

        Flux<String> stringFlux = Flux.just("A", "B", "C");
        Mono<String> dataNewly = stringFlux.next();
        dataNewly.subscribe(System.out::println);
    }

    public List<String> createList(int count) {

        List<String> listOFData=new ArrayList<>();
        for (int i = 0; i <=count; i++) {
            listOFData.add(Faker.instance().name().firstName());
        }
        return listOFData;
    }

    public String getName() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return Faker.instance().name().firstName();

    }
}
