package com.green.springbootjwt.service.impl;

import com.green.springbootjwt.entity.Item;
import com.green.springbootjwt.repo.ItemRepository;
import com.green.springbootjwt.request.ItemRequest;
import com.green.springbootjwt.security.CurrentUserDetails;
import com.green.springbootjwt.service.IItemService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class ItemServiceImpl implements IItemService {


    private final ModelMapper modelMapper;
    private final ItemRepository itemRepository;
    private final CurrentUserDetails currentUserDetails;

    public ItemServiceImpl(ModelMapper modelMapper, ItemRepository itemRepository, CurrentUserDetails currentUserDetails) {
        this.modelMapper = modelMapper;
        this.itemRepository = itemRepository;
        this.currentUserDetails = currentUserDetails;
    }

    @Override
    public ItemRequest createItem(ItemRequest itemRequest) {

        final Long userId = this.currentUserDetails.currentUser().getId();

        return this.itemRepository.findById(itemRequest.getId())
                .map(item -> {

                    item.setName(itemRequest.getName());
                    item.setPrice(itemRequest.getPrice());
                    item.setUpdatedBy(userId);
                    this.itemRepository.save(item);
                    return itemRequest;
                })
                .orElseGet(() -> {

                    Item item = modelMapper.map(itemRequest, Item.class);
                    item.setCreatedBy(userId);
                    item.setUpdatedBy(userId);
                    Item save = this.itemRepository.save(item);
                    return this.modelMapper.map(save, ItemRequest.class);

                });
    }
}
