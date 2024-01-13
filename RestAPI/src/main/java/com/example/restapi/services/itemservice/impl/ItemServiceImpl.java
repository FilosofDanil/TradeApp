package com.example.restapi.services.itemservice.impl;

import com.example.restapi.dtos.ItemDTO;
import com.example.restapi.mappers.ItemMapper;
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
    public List<ItemDTO> getAllByName(String name) {
        return itemRepo.findByUserTgName(name)
                .stream()
                .map(ItemMapper::toModel)
                .toList();
    }

    @Override
    public List<ItemDTO> getAllByUser(String username) {
        return itemRepo.findByUserUsername(username)
                .stream()
                .map(ItemMapper::toModel)
                .toList();
    }
}
