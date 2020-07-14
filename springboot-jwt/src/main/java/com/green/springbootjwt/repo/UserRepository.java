package com.green.springbootjwt.repo;

import com.green.springbootjwt.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Integer> {

    Optional<User> findByEmail(String email);
    Optional<User> findByRefreshToken(String refreshToken);
}
