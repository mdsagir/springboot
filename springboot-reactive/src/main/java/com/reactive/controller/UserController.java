package com.reactive.controller;

import com.reactive.document.Address;
import com.reactive.document.Product;
import com.reactive.document.Request;
import com.reactive.document.User;
import com.reactive.model.ChartLineRequest;
import com.reactive.model.TickerValue;
import com.reactive.service.LineChartService;
import com.reactive.service.UserService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("v1/user")
public class UserController {


    private final LineChartService lineChartService;
    private final UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<User> saveUser(@RequestBody User user) {
        return userService.saveUser(user);
    }

    @GetMapping
    public Flux<User> getAllUsers() {
        log.info("get All Value");
        return userService.getAllUsers();
    }

    @GetMapping("{id}")
    public Mono<User> getUserById(@PathVariable String id) {
        return userService.getUserById(id);
    }

    @GetMapping("name/{name}")
    public Flux<User> getUserByName(@PathVariable String name) {
        return userService.findByName(name);
    }

    @GetMapping("pro")
    public Flux<Product> getUserByName() {

        Request request = new Request();
        request.setIntegers(List.of(1, 2));

        List<Product> list = new ArrayList<>();
        request.getIntegers().forEach(integer -> {

            Product product = new Product();
            product.setId(integer.toString());

            List<Address> addresses = new ArrayList<>();
            Flux<Address> addressFlux = Flux.just(
                    new Address(1),
                    new Address(2));
            addressFlux.subscribe(addresses::add);
            product.setAddress(addresses);

            list.add(product);
        });

        return Flux.fromIterable(list);
    }

    @GetMapping("pro2")
    public Flux<Product> getUserByName2() {

        Request request = new Request();
        request.setIntegers(List.of(1, 2));

        return Flux.fromIterable(request.getIntegers()).flatMap(integers -> {

            Product product = new Product();
            product.setId(integers.toString());

            Mono<Product> productMono = Mono.just(product);

            Flux<Address> addressFlux = Flux.just(
                    new Address(1),
                    new Address(2));

            return Mono.zip(productMono, addressFlux.collectList(), (x, y) -> {
                x.setAddress(y);
                return x;
            });
        });

    }

    @PostMapping("charts")
    public Flux<TickerValue> processLineChartsByIds(@RequestBody ChartLineRequest chartLineRequest) {
        log.info("processLineChartsByIds inside LineChartControllerV2");
        return lineChartService.processLineChartsByIds(chartLineRequest);
    }

    @GetMapping("defaultThreading")
    public Mono<String> defaultThreading() {
        System.out.println(" Request :) Thread Name: " + Thread.currentThread().getName());
        Flux<Object> objectFlux = Flux.create(fluxSink -> {
            fluxSink.next(1);
        }).doOnNext(i -> printThreadName("next :" + i));
        objectFlux.subscribe(s -> printThreadName("sub :" + s));

        return Mono.just("defaultThreading");
    }

    private void printThreadName(String name) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(name + " :) Thread Name: " + Thread.currentThread().getName());
    }

    @GetMapping("schedule")
    public Flux<User> sheduleOn() {
        Flux<User> number = getNumber();//.subscribeOn(Schedulers.parallel());
        return number;
    }

    public Flux<User> getNumber() {
        return Flux.create(fluxSink -> {
            for (int i = 1; i <= 5; i++) {
                try {
                    System.out.println("Thread name " + Thread.currentThread().getName());
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                fluxSink.next(new User(String.valueOf(i)));
            }
            fluxSink.complete();
        });
    }


}
