package com.reactive.service;

import com.reactive.common.ExceptionProvider;
import com.reactive.document.User;
import com.reactive.repo.UserCrudRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserCrudRepository userCrudRepository;

    @Override
    public Mono<User> saveUser(User user) {
        return userCrudRepository.save(user)
                .switchIfEmpty(ExceptionProvider.handlerPublisher(": problem to save data"));
    }

    @Override
    public Flux<User> getAllUsers() {
        return this.userCrudRepository.findAll()
                .switchIfEmpty(ExceptionProvider.handleException("Data not found for all"));
                 //.switchIfEmpty(ExceptionProvider.handlerPublisher("getAllUser"));
    }

    @Override
    public Mono<User> getUserById(String id) {
        return this.userCrudRepository.findById(id)
                .switchIfEmpty(ExceptionProvider.handleException("Data not found "+id));
                //.switchIfEmpty(ExceptionProvider.handlerPublisher(id));
    }

    @Override
    public Flux<User> findByName(String name) {
        return this.findByName(name)
                .switchIfEmpty(ExceptionProvider.handlerPublisher(name));
    }
}
