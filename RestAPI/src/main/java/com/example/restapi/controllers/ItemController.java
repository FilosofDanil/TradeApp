package com.example.restapi.controllers;

import com.example.restapi.dtos.ItemDTO;
import com.example.restapi.dtos.TelegramUserDTO;
import com.example.restapi.dtos.UpdateItemDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/items")
@RequiredArgsConstructor
public class ItemController {
    @GetMapping("")
    public ResponseEntity<List<ItemDTO>> getAllItems() {
        return null;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemDTO> getItemById(@PathVariable Long id) {
        return null;
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<List<ItemDTO>> getAllItemsByUser(@PathVariable String username) {
        return null;
    }

    @GetMapping("/{name}")
    public ResponseEntity<ItemDTO> getItemByName(@PathVariable String name) {
        return null;
    }

    @PostMapping("")
    public ResponseEntity<ItemDTO> createItem(@RequestBody ItemDTO item){
        return null;
    }

    @PutMapping("/{id}")
    public ResponseEntity<HttpStatus> updateItem(@RequestBody UpdateItemDTO item, @PathVariable Long id) {
        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteItem(@PathVariable Long id) {
        return null;
    }
}
