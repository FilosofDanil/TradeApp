package com.example.restapi.services.itemservice;

import com.example.restapi.dtos.ItemDTO;

import java.util.List;

public interface ItemService {
    List<ItemDTO> getAllByName(String name);

    List<ItemDTO> getAllByUser(String username);
}
