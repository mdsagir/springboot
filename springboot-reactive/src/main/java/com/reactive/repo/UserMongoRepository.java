package com.reactive.repo;

import com.reactive.document.User;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface UserMongoRepository extends ReactiveMongoRepository<User,String> {
}
