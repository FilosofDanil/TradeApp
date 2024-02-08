package com.example.restapi.services.crud.impl;

import com.example.restapi.dtos.ItemDTO;
import com.example.restapi.entites.Item;
import com.example.restapi.exceptions.ResourceNotFoundException;
import com.example.restapi.repositories.ItemRepository;
import com.example.restapi.services.crud.CRUDItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CRUDItemServiceImpl implements CRUDItemService {
    private final ItemRepository itemRepo;

    @Override
    public List<Item> getAll() {
        return itemRepo.findAll();
    }

    @Override
    public Item getById(Long id) {
        if (!itemRepo.existsById(id)) {
            throw new ResourceNotFoundException("Resource is not available");
        }
        return itemRepo.findById(id).get();
    }

    @Override
    public Item create(Item item) {
        item.setBidPrice(0);
        item.setExpired(false);
        return itemRepo.save(item);
    }

    @Override
    @Transactional
    public void update(Long id, Item item) {
        if (!itemRepo.existsById(id)) {
            itemRepo.save(item);
        } else {
            itemRepo.updateItem(item.getItemName(),
                    item.getDescription(), item.getExpirationDate(),
                    item.getPlacementDate(), item.getBidPrice(),
                    item.getStartPrice(), item.getExpired(), id);
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
