package com.example.tradeapp.components;

import com.example.tradeapp.entities.models.Settings;

import java.util.Map;

public interface SettingsComponent {
    Settings saveSettings(String username, Map<String, String> data);

    Settings getSettingsByUsername(String username);
}
