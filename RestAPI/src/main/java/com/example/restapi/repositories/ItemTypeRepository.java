package com.example.restapi.repositories;

import com.example.restapi.entites.ItemType;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ItemTypeRepository extends CrudRepository<ItemType, Long> {
    ItemType findByName(String name);

    List<ItemType> findAll();
}
