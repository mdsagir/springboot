package com.reactive.repo;


import com.reactive.document.User;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface UserCrudRepository extends ReactiveCrudRepository<User,String> {

    Flux<User> findByName(String name);
}
