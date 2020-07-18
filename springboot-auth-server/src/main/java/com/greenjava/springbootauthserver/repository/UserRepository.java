package com.greenjava.springbootauthserver.repository;

import com.greenjava.springbootauthserver.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findByUsername(String userName);
}

