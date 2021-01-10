package com.reactive.controller;

import com.reactive.common.ExceptionProvider;
import com.reactive.document.User;
import com.reactive.repo.UserCrudRepository;
import com.reactive.repo.UserMongoRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@RestController
@RequestMapping("v1/user")
public class UserController {

    private final UserCrudRepository userCrudRepository;
    private final UserMongoRepository userMongoRepository;
    private final ReactiveMongoTemplate reactiveMongoTemplate;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<User> saveUser(@RequestBody User user) {
        return reactiveMongoTemplate.save(user)
                .switchIfEmpty(ExceptionProvider.handlerPublisher(": problem to save data"));
    }

    @GetMapping
    public Flux<User> getAllUsers() {
        return userMongoRepository.findAll()
                .switchIfEmpty(ExceptionProvider.handlerPublisher("getAllUser"));
    }

    @GetMapping("{id}")
    public Mono<User> getUserById(@PathVariable String id) {
        return reactiveMongoTemplate.findById(id, User.class)
                .switchIfEmpty(ExceptionProvider.handlerPublisher(id));
    }

    @GetMapping("name/{name}")
    public Flux<User> getUserByName(@PathVariable String name) {
        return userCrudRepository.findByName(name)
                .switchIfEmpty(ExceptionProvider.handlerPublisher(name));
    }

}
