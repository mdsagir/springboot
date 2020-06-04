package com.greenjava.validationexception.repo;

import com.greenjava.validationexception.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;

public interface ItemRepo extends JpaRepository<Item,Integer> {

}
