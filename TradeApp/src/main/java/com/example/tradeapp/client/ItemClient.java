package com.example.tradeapp.client;

import com.example.tradeapp.entities.models.Items;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "itemClient", url = "${application.config.items}")
public interface ItemClient {
    @GetMapping("")
    List<Items> getAllItems();

    @GetMapping("/{id}")
    Items getItemById(@PathVariable Long id);

    @GetMapping("/user/{username}")
    List<Items> getAllItemsByUser(@PathVariable String username);

    @GetMapping("name/{name}")
    List<Items> getItemByName(@PathVariable String name);

    @PostMapping("")
    Items createItem(@RequestBody Items item);

    @PutMapping("/{id}")
    void updateItem(@RequestBody Items item, @PathVariable Long id);

    @DeleteMapping("/{id}")
    void deleteItem(@PathVariable Long id);
}
