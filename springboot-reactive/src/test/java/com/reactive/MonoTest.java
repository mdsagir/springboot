package com.reactive;

import org.junit.jupiter.api.Test;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.List;
import java.util.Optional;
import java.util.function.LongConsumer;
import java.util.stream.Stream;

/**
 * Reactive Stream<p>
 * 1. Asynchronous <p>
 * 2. Non- Blocking<p>
 * 3. Back Pressure <p>
 * Publisher : -
 * <pre><code>
 *  public interface Publisher<T> {
 *   public void subscribe(Subscriber<? super T> s);
 *  }
 * </code></pre>
 *
 * When Subscriber subscribe from Publisher then Subscription will be created
 */

public class MonoTest {

    @Test
    void mono_subscribe_with_lambada_test() {

        Mono<String> stringMono = Mono.just("Spring").log();

        stringMono.subscribe(
                        s -> System.out.println("Element "+s), // OnNext
                        throwable -> System.err.println("Error: " + throwable.getMessage()), //OnError
                        () -> System.out.println("OnComplete"), // OnComplete

                        subscription -> subscription.request(1) // OnSubscribe
        );
    }

    @Test
    void mono_subscribe_with_on_next_test() {

        Mono<String> stringMono = Mono.just("Spring").log();
        Mono<String> stringMono1 = stringMono.doOnSubscribe(subscription -> subscription.request(1));
        Mono<String> stringMono2 = stringMono.doOnNext(s -> System.out.println("Element " + s));
        Mono<String> stringMono3 = stringMono.doOnError(Throwable::getMessage);

        Mono<String> stringMono4 = stringMono.doOnRequest(value -> System.out.println("On request"));
        Mono<String> stringMono5 = stringMono.doOnCancel(() -> System.out.println("Completed"));
        // doOnComplete() not available for the Mono Publisher
        Mono<String> stringMono6 = stringMono.doOnSuccess(s -> System.out.println("Do on success " + s));

    }

    @Test
    void mono_publish_on_subscribe_on() {

        Mono<String> spring = Mono.just("Spring");

        spring.publishOn(Schedulers.parallel()).subscribe(System.out::println);

    }

    @Test
    void mono_subscribers() {

        Mono<String> stringMono = Mono.just("Spring").log();

         stringMono.subscribe(new Subscriber<>() {
             @Override
             public void onSubscribe(Subscription s) {
                s.request(2);
             }

             @Override
             public void onNext(String s) {
                 System.out.println("subscribing "+s);
             }

             @Override
             public void onError(Throwable t) {

             }

             @Override
             public void onComplete() {
                 System.out.println("Completed ###");
             }
         });
    }

    @Test
    void mono_do_on_success() {

        Mono<String> stringMono = Mono.just("String");

    }

    @Test
    void mono_merge() {

    }
}
