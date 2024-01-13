package com.example.restapi.controllers;

import com.example.restapi.dtos.ItemDTO;
import com.example.restapi.dtos.TelegramUserDTO;
import com.example.restapi.dtos.UpdateItemDTO;
import com.example.restapi.services.crud.CRUDService;
import com.example.restapi.services.itemservice.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/items")
@RequiredArgsConstructor
public class ItemController {
    private final CRUDService<ItemDTO> crudService;

    private final ItemService itemService;

    @GetMapping("")
    public ResponseEntity<List<ItemDTO>> getAllItems() {
        return ResponseEntity.ok(crudService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemDTO> getItemById(@PathVariable Long id) {
        return ResponseEntity.ok(crudService.getById(id));
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<List<ItemDTO>> getAllItemsByUser(@PathVariable String username) {
        return ResponseEntity.ok(itemService.getAllByUser(username));
    }

    @GetMapping("/{name}")
    public ResponseEntity<List<ItemDTO>> getItemByName(@PathVariable String name) {
        return ResponseEntity.ok(itemService.getAllByName(name));
    }

    @PostMapping("")
    public ResponseEntity<ItemDTO> createItem(@RequestBody ItemDTO item) {
        return new ResponseEntity<>(crudService.create(item), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HttpStatus> updateItem(@RequestBody ItemDTO item, @PathVariable Long id) {
        crudService.update(id, item);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteItem(@PathVariable Long id) {
        crudService.delete(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
