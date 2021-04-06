package com.reactive.redis.reactiveredis.service;

import com.reactive.redis.reactiveredis.model.Coffee;
import com.reactive.redis.reactiveredis.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveHashOperations;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CoffeeLoader {

    private final ReactiveRedisConnectionFactory factory;
    private final ReactiveRedisOperations<String, String> coffeeOps;


    @PostConstruct
    public void loadData() {
        //factory.getReactiveConnection().serverCommands().flushAll().subscribe();
        ReactiveHashOperations<String, String, User> ops = coffeeOps.opsForHash();

        Flux.just("Jet Black Redis", "Darth Redis", "Black Alert Redis")
                .map(name -> new User(UUID.randomUUID().toString(), name,10))
                .flatMap(coffee -> ops.put("data", coffee.getId(), coffee))
        .subscribe();

        /*Mono<User> coffeeMono = ops.get("data", "b84f7dc8-9045-4686-b776-f95d042e08d9");
        coffeeMono.subscribe(System.out::println);*/

        /*Mono<Long> data = ops.remove("data", "b84f7dc8-9045-4686-b776-f95d042e08d9");
        data.subscribe(aLong -> System.out.println("Data deleted " +aLong));*/


        /*factory.getReactiveConnection().serverCommands().flushAll().thenMany(
                Flux.just("Jet Black Redis", "Darth Redis", "Black Alert Redis")
                        .map(name -> new Coffee(UUID.randomUUID().toString(), name))
                        .flatMap(coffee -> ops.put("data",coffee.getId(), coffee)))
                .thenMany(ops.keys("*")
                        .flatMap(ops::values))
                .subscribe(System.out::println);*/
    }
}
