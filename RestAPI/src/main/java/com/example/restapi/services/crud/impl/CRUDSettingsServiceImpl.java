package com.example.restapi.services.crud.impl;

import com.example.restapi.entites.Settings;
import com.example.restapi.exceptions.ResourceNotFoundException;
import com.example.restapi.repositories.SettingsRepository;
import com.example.restapi.services.crud.CRUDSettingsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CRUDSettingsServiceImpl implements CRUDSettingsService {
    private final SettingsRepository settingsRepository;

    @Override
    public List<Settings> getAll() {
        return settingsRepository.findAll();
    }

    @Override
    public Settings getById(Long id) {
        if (!settingsRepository.existsById(id)) {
            throw new ResourceNotFoundException("Resource is not available");
        }
        return settingsRepository.findById(id).get();
    }

    @Override
    public Settings create(Settings settings) {
        return settingsRepository.save(settings);
    }

    @Override
    public void update(Long id, Settings settings) {
        if(!settingsRepository.existsById(id)){
            settingsRepository.save(settings);
        } else {
            Settings toUpdate = settingsRepository.findById(id).get();
            toUpdate.setCity(settings.getCity());
            toUpdate.setUser(settings.getUser());
            toUpdate.setItemTypes(settings.getCategories());
            settingsRepository.save(toUpdate);
        }
    }

    @Override
    public void delete(Long id) {
        settingsRepository.deleteById(id);
    }
}
