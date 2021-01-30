package com.reactive.service;

import com.reactive.document.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserService {

    Mono<User> saveUser(User user);

    Flux<User> getAllUsers();

    Mono<User> getUserById(String id);

    Flux<User> findByName(String name);
}
