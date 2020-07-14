package com.green.springbootjwt.controller;

import com.green.springbootjwt.request.ItemRequest;
import com.green.springbootjwt.service.IItemService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ItemController {


    private final IItemService itemService;

    public ItemController(IItemService itemService) {
        this.itemService = itemService;
    }


    @PostMapping("item")
    public ItemRequest item(@RequestBody ItemRequest itemRequest) {
       return itemService.createItem(itemRequest);
    }

}
