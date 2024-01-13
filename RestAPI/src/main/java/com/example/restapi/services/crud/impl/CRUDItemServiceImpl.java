package com.example.restapi.services.crud.impl;

import com.example.restapi.dtos.ItemDTO;
import com.example.restapi.entites.Item;
import com.example.restapi.mappers.ItemMapper;
import com.example.restapi.mappers.UserMapper;
import com.example.restapi.repositories.ItemRepository;
import com.example.restapi.services.crud.CRUDItemService;
import com.example.restapi.services.crud.CRUDService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CRUDItemServiceImpl implements CRUDItemService {
    private final ItemRepository itemRepo;

    @Override
    public List<ItemDTO> getAll() {
        return itemRepo.findAll()
                .stream()
                .map(ItemMapper::toModel)
                .toList();
    }

    @Override
    public ItemDTO getById(Long id) {
        return itemRepo.findById(id)
                .map(ItemMapper::toModel).get();
    }

    @Override
    public ItemDTO create(ItemDTO itemDTO) {
        return ItemMapper.toModel(newItem(itemDTO));
    }

    @Override
    public void update(Long id, ItemDTO itemDTO) {
        if (itemRepo.findById(id).isEmpty()) {
            newItem(itemDTO);
        } else {
            itemRepo.updateItem(itemDTO.getItemName(),
                    itemDTO.getDescription(),
                    itemDTO.getExpirationDate(),
                    itemDTO.getPlacementDate(), id);
        }
    }

    @Override
    public void delete(Long id) {
        itemRepo.deleteById(id);
    }

    private Item newItem(ItemDTO itemDTO) {
        return itemRepo.create(itemDTO.getItemName(),
                itemDTO.getDescription(), itemDTO.getStartPrice(),
                itemDTO.getPlacementDate(), itemDTO.getExpirationDate(),
                itemDTO.getUserId());
    }
}
