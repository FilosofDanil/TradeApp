package com.example.restapi.services.itemservice;

import com.example.restapi.dtos.ItemDTO;
import com.example.restapi.entites.Item;

import java.util.List;

public interface ItemService {
    List<Item> getAllByName(String name);

    List<Item> getAllByUser(String username);

    List<Item> getAllByUserHavingBids(String username);
}
