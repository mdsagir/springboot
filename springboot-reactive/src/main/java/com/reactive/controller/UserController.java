package com.reactive.controller;

import com.reactive.document.Address;
import com.reactive.document.Product;
import com.reactive.document.Request;
import com.reactive.document.User;
import com.reactive.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("v1/user")
public class UserController {


    private final UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<User> saveUser(@RequestBody User user) {
        return userService.saveUser(user);
    }

    @GetMapping
    public Flux<User> getAllUsers() {
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

        Request request=new Request();
        request.setIntegers(List.of(1,2));

        List<Product> list=new ArrayList<>();
        request.getIntegers().forEach(integer -> {

            Product product=new Product();
            product.setId(integer.toString());

            List<Address> addresses=new ArrayList<>();
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

        Request request=new Request();
        request.setIntegers(List.of(1,2));

       return Flux.fromIterable(request.getIntegers()).flatMap(integers -> {

            Product product=new Product();
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



}
