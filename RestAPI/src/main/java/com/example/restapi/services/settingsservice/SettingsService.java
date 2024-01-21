package com.example.restapi.services.settingsservice;

import com.example.restapi.dtos.SettingsDTO;
import com.example.restapi.entites.Settings;

public interface SettingsService {
    Settings getByUsername(String username);

    Settings toEntity(SettingsDTO settingsDTO);
}
