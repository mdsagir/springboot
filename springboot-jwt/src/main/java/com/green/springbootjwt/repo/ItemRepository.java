package com.green.springbootjwt.repo;

import com.green.springbootjwt.entity.Item;
import org.springframework.data.repository.CrudRepository;

public interface ItemRepository extends CrudRepository<Item,Integer> {
}
