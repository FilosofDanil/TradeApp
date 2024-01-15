package com.example.restapi.controllers;

import com.example.restapi.dtos.ItemDTO;
import com.example.restapi.dtos.SettingsDTO;
import com.example.restapi.entites.Item;
import com.example.restapi.entites.Settings;
import com.example.restapi.services.crud.CRUDService;
import com.example.restapi.services.itemservice.ItemService;
import com.example.restapi.services.settingsservice.SettingsService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/settings")
public class SettingsController {
    private final CRUDService<Settings> crudService;

    private final SettingsService settingsService;

    private final ModelMapper modelMapper;

    @GetMapping("")
    public ResponseEntity<List<SettingsDTO>> getAllSettings() {
        return ResponseEntity.ok(crudService.getAll()
                .stream()
                .map(this::toDto)
                .toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SettingsDTO> getSettingsById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(toDto(crudService.getById(id)));
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<SettingsDTO> getSettingsByUsername(@PathVariable("username") String username) {
        return ResponseEntity.ok(toDto(settingsService.getByUsername(username)));
    }

    @PostMapping("")
    public ResponseEntity<SettingsDTO> createSettings(@RequestBody SettingsDTO settingsDTO) {
        Settings settings = settingsService.toEntity(settingsDTO);
        return new ResponseEntity<>(toDto(crudService.create(settings)), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HttpStatus> updateSettings(@PathVariable("id") Long id, @RequestBody SettingsDTO settingsDTO) {
        Settings settings = settingsService.toEntity(settingsDTO);
        crudService.update(id, settings);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteSettings(@PathVariable("id") Long id) {
        crudService.delete(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    private SettingsDTO toDto(Settings settings) {
        return modelMapper.map(settings, SettingsDTO.class);
    }
}
