package com.green.springbootjwt.service;

import com.green.springbootjwt.request.ItemRequest;

public interface IItemService {

    ItemRequest createItem(ItemRequest itemRequest);
}
