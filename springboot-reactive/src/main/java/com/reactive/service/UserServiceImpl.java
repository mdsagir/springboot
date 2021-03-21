package com.reactive.service;

import com.reactive.common.ExceptionProvider;
import com.reactive.document.User;
import com.reactive.repo.UserCrudRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserCrudRepository userCrudRepository;

    @Override
    public Mono<User> saveUser(User user) {
        return userCrudRepository.save(user)
                .switchIfEmpty(ExceptionProvider.handleException(": problem to save data"));
    }

    @Override
    public Flux<User> getAllUsers() {
        return this.userCrudRepository.findAll()
                .switchIfEmpty(ExceptionProvider.handleException("Data not found for all"));

    }

    @Override
    public Mono<User> getUserById(String id) {
        return this.userCrudRepository.findById(id)
                .flatMap(user -> {
                    int n = 10 / 0;
                    return Mono.just(user);
                })
                .onErrorResume(ExceptionProvider::somethingWentWrong)
                .switchIfEmpty(ExceptionProvider.handleException("Data not found " + id));

    }

    @Override
    public Flux<User> findByName(String name) {
        return this.findByName(name);
    }
}
