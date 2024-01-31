package com.example.restapi.services.itemservice.impl;

import com.example.restapi.entites.Item;
import com.example.restapi.repositories.ItemRepository;
import com.example.restapi.services.itemservice.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepo;

    @Override
    public List<Item> getAllByName(String name) {
        return itemRepo.findByUserTgName(name);
    }

    @Override
    public List<Item> getAllByUser(String username) {
        return itemRepo.findByUserUsername(username);
    }

    @Override
    public List<Item> getAllByUserHavingBids(String username) {
        return itemRepo.getItemsByUserHavingBids(username);
    }
}
