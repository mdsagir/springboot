package com.greenjava.springbootauthserver.repository;

import com.greenjava.springbootauthserver.entity.Client;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ClientRepository extends CrudRepository<Client,Long> {

    Optional<Client> findByClientId(String clientId);
}
