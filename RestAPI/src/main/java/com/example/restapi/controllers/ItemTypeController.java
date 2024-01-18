package com.example.restapi.controllers;

import com.example.restapi.dtos.BidDTO;
import com.example.restapi.dtos.ItemTypeDTO;
import com.example.restapi.entites.ItemType;
import com.example.restapi.services.crud.CRUDService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/itemTypes")
public class ItemTypeController {
    private final CRUDService<ItemType> crudService;

    private final ModelMapper modelMapper;

    @GetMapping("")
    public ResponseEntity<List<ItemTypeDTO>> getAllItemTypes() {
        return ResponseEntity.ok(crudService.getAll()
                .stream()
                .map(this::toDto)
                .toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemTypeDTO> getItemTypeById(@PathVariable Long id) {
        return ResponseEntity.ok(toDto(crudService.getById(id)));
    }

    @PostMapping("")
    public ResponseEntity<ItemTypeDTO> createItemType(@RequestBody ItemTypeDTO item) {
        return new ResponseEntity<>(toDto(crudService.create(toEntity(item))), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HttpStatus> updateBid(@RequestBody ItemTypeDTO item, @PathVariable Long id) {
        crudService.update(id, toEntity(item));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteBid(@PathVariable Long id) {
        crudService.delete(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    private ItemTypeDTO toDto(ItemType itemType) {
        return modelMapper.map(itemType, ItemTypeDTO.class);
    }

    private ItemType toEntity(ItemTypeDTO itemTypeDTO) {
        return modelMapper.map(itemTypeDTO, ItemType.class);
    }
}
