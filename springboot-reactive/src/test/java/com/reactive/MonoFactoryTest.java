package com.reactive;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.concurrent.CompletableFuture;

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
 * <p>
 * When Subscriber subscribe from Publisher then Subscription will be created
 *
 * 1.Mono.just()
 * 2.Mono.fromSupplier()
 * 3.Mono.fromCallable()
 * 4.Mono.fromFuture()
 * 5.Mono.fromRunnable()
 */

public class MonoFactoryTest {


    @Test
    void mono_just() {
        Mono<Integer> mono = Mono.just(1).log();
        mono.subscribe(System.out::println);
    }



    @Test
    void mono_suppliers() {

        Mono<String> mono = Mono.fromSupplier(this::getName);
        mono.subscribe(System.out::println);
    }

    @Test
    void mono_callable() {

        Mono<String> mono = Mono.fromCallable(this::getName);
        mono.subscribe(System.out::println);
    }

    @Test
    void mono_supplier_pipeline() throws InterruptedException {
        getFirstName();
        getFirstName()
                .subscribeOn(Schedulers.boundedElastic())
                .subscribe(System.out::println);
        getFirstName();
        Thread.sleep(2000);
    }

    @Test
    void mono_completable_futures() throws InterruptedException {

        Mono.fromFuture(getLastName()).subscribe(System.out::println);
        Thread.sleep(100);
    }

    @Test
    void mono_runnable() {
        Mono.fromRunnable(timeConsumingTask())
                .subscribe(System.out::println,
                        System.err::println,
                        () -> System.out.println("Process to sending email"));

    }

    private Runnable timeConsumingTask() {
        return () -> {
            try {
                System.out.println("Calculating heavy calculations");
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        };
    }
    public CompletableFuture<String> getLastName() {
        return CompletableFuture.supplyAsync(() ->
                Faker.instance().name().lastName());
    }

    private Mono<String> getFirstName() {
        System.out.println("inside getFirstName() methods");
        return Mono.fromSupplier(() -> {
            System.out.println("Creating a name ");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
            return Faker.instance().name().firstName();
        });
    }

    private String getName() {
        System.out.println("Name is generating");
        String s = Faker.instance().name().fullName();
        System.out.println("Name is here " + s);
        return s;
    }
}
