package com.example.restapi.controllers;

import com.example.restapi.dtos.ItemDTO;
import com.example.restapi.dtos.SettingsDTO;
import com.example.restapi.entites.Item;
import com.example.restapi.entites.Settings;
import com.example.restapi.services.crud.CRUDService;
import com.example.restapi.services.itemservice.ItemService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/settings")
public class SettingsController {
    private final CRUDService<Settings> crudService;

    private final ItemService itemService;

    private final ModelMapper modelMapper;

    @GetMapping("")
    public ResponseEntity<List<SettingsDTO>> getAllItems() {
        return ResponseEntity.ok(crudService.getAll()
                .stream()
                .map(this::toDto)
                .toList());
    }

    private SettingsDTO toDto(Settings settings) {
        return modelMapper.map(settings, SettingsDTO.class);
    }
}
