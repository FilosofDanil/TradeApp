package com.example.tradeapp.client;

import com.example.tradeapp.entities.models.ItemType;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "itemTypeClient", url = "${application.config.itemTypes}")
public interface ItemTypeClient {
    @GetMapping("")
    List<ItemType> getAllItemTypes();
}
