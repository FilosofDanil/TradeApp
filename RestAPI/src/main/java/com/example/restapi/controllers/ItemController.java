package com.example.restapi.controllers;

import com.example.restapi.dtos.ItemDTO;
import com.example.restapi.entites.Item;
import com.example.restapi.services.crud.CRUDService;
import com.example.restapi.services.itemservice.ItemService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/items")
@RequiredArgsConstructor
public class ItemController {
    private final CRUDService<Item> crudService;

    private final ItemService itemService;

    private final ModelMapper modelMapper;

    @GetMapping("")
    public ResponseEntity<List<ItemDTO>> getAllItems() {
        return ResponseEntity.ok(crudService.getAll()
                .stream()
                .map(this::toDto)
                .toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemDTO> getItemById(@PathVariable Long id) {
        return ResponseEntity.ok(toDto(crudService.getById(id)));
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<List<ItemDTO>> getAllItemsByUser(@PathVariable String username) {
        return ResponseEntity.ok(itemService.getAllByUser(username).stream()
                .map(this::toDto)
                .toList());
    }

    @GetMapping("/{name}")
    public ResponseEntity<List<ItemDTO>> getItemByName(@PathVariable String name) {
        return ResponseEntity.ok(itemService.getAllByName(name).stream()
                .map(this::toDto)
                .toList());
    }

    @PostMapping("")
    public ResponseEntity<ItemDTO> createItem(@RequestBody ItemDTO item) {
        return new ResponseEntity<>(toDto(crudService.create(toEntity(item))), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HttpStatus> updateItem(@RequestBody ItemDTO item, @PathVariable Long id) {
        crudService.update(id, toEntity(item));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteItem(@PathVariable Long id) {
        crudService.delete(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    private ItemDTO toDto(Item item) {
        return modelMapper.map(item, ItemDTO.class);
    }

    private Item toEntity(ItemDTO itemDTO) {
        return modelMapper.map(itemDTO, Item.class);
    }
}
