package com.example.restapi.services.crud.impl;

import com.example.restapi.entites.ItemType;
import com.example.restapi.entites.Settings;
import com.example.restapi.repositories.ItemTypeRepository;
import com.example.restapi.services.crud.CRUDItemTypeService;
import com.example.restapi.services.crud.CRUDService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CRUDItemTypeServiceImpl implements CRUDItemTypeService {
    private final ItemTypeRepository itemTypeRepo;

    @Override
    public List<ItemType> getAll() {
        return itemTypeRepo.findAll();
    }

    @Override
    public ItemType getById(Long id) {
        return itemTypeRepo.findById(id).get();
    }

    @Override
    public ItemType create(ItemType itemType) {
        return itemTypeRepo.save(itemType);
    }

    @Override
    public void update(Long id, ItemType itemType) {
        if (!itemTypeRepo.existsById(id)) {
            itemTypeRepo.save(itemType);
        } else {
            ItemType toUpdate = itemTypeRepo.findById(id).get();
            toUpdate.setName(itemType.getName());
            itemTypeRepo.save(toUpdate);
        }
    }

    @Override
    public void delete(Long id) {
        itemTypeRepo.deleteById(id);
    }
}
