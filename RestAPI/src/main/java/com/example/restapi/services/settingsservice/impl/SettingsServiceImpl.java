package com.example.restapi.services.settingsservice.impl;

import com.example.restapi.dtos.SettingsDTO;
import com.example.restapi.entites.Item;
import com.example.restapi.entites.ItemType;
import com.example.restapi.entites.Settings;
import com.example.restapi.entites.TelegramUser;
import com.example.restapi.exceptions.ResourceNotFoundException;
import com.example.restapi.repositories.ItemTypeRepository;
import com.example.restapi.repositories.SettingsRepository;
import com.example.restapi.repositories.TelegramUserRepository;
import com.example.restapi.services.crud.CRUDService;
import com.example.restapi.services.settingsservice.SettingsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class SettingsServiceImpl implements SettingsService {
    private final SettingsRepository settingsRepo;

    private final ItemTypeRepository itemTypeRepo;

    private final CRUDService<TelegramUser> userCRUDService;

    @Override
    public Settings getByUsername(String username) {
        if(settingsRepo.findByUserName(username) == null){
            throw new ResourceNotFoundException("Resource is not available");
        }
        return settingsRepo.findByUserName(username);
    }

    @Override
    public Settings toEntity(SettingsDTO settingsDTO) {
        Set<ItemType> itemTypes = new HashSet<>();
        settingsDTO.getCategories().forEach(itemType -> {
            itemTypes.add(itemTypeRepo.findByName(itemType));
        });
        return Settings
                .builder()
                .city(settingsDTO.getCity())
                .user(userCRUDService.getById(settingsDTO.getUserId()))
                .categories(itemTypes)
                .build();
    }
}
