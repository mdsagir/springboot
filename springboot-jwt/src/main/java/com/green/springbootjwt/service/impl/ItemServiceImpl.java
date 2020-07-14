package com.green.springbootjwt.service.impl;

import com.green.springbootjwt.entity.Item;
import com.green.springbootjwt.repo.ItemRepository;
import com.green.springbootjwt.request.ItemRequest;
import com.green.springbootjwt.service.IItemService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class ItemServiceImpl implements IItemService {


    private final ModelMapper modelMapper;
    private final ItemRepository itemRepository;

    public ItemServiceImpl(ModelMapper modelMapper, ItemRepository itemRepository) {
        this.modelMapper = modelMapper;
        this.itemRepository = itemRepository;
    }

    @Override
    public ItemRequest createItem(ItemRequest itemRequest) {
        final Item item = this.modelMapper.map(itemRequest, Item.class);
        item.setId(2);
        item.setAction(3);
        return modelMapper.map(this.itemRepository.save(item), ItemRequest.class);
    }
}
