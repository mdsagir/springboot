package com.greenjava.validationexception.controller;

import com.greenjava.validationexception.model.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ItemController {

    @Autowired
    private ItemService itemService;

    @GetMapping("item")
    public Item getItem() {
        return new Item(1, "box", 12,12);
    }
    @GetMapping("item-service")
    public Item getItemService() {
        return itemService.createItem();
    }
    @GetMapping("item-db-service")
    public List<Item> getItemDbService() {
        return itemService.retrieveAllItems();
    }
}
