package com.greenjava.validationexception.controller;

import com.greenjava.validationexception.model.Item;
import com.greenjava.validationexception.repo.ItemRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {

    @Autowired
    private ItemRepo itemRepo;

    public Item createItem() {
        return new Item(1, "box", 12,12);
    }


    public List<Item> retrieveAllItems() {
        return itemRepo.findAll();
    }
}
