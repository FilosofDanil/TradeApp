package com.example.tradeapp.components.impl;

import com.example.tradeapp.client.SettingsClient;
import com.example.tradeapp.client.UserClient;
import com.example.tradeapp.components.SettingsComponent;
import com.example.tradeapp.entities.models.Settings;
import com.example.tradeapp.entities.models.Users;
import com.example.tradeapp.entities.session.UserSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class SettingsComponentImpl implements SettingsComponent {
    private final SettingsClient settingsClient;

    private final UserClient userClient;

    @Override
    public Settings saveSettings(String username, Map<String, String> data) {
        //later add update of settings
        String city = data.get("city");
        data.remove("city");
        data.remove("");
        List<String> categories = new ArrayList<>(data.values());
        Users user = userClient.getUserByUsername(username);
        Settings settings = Settings.builder()
                .userId(user.getId())
                .city(city)
                .categories(categories)
                .build();
        return settingsClient.createSettings(settings);
    }

    @Override
    public Settings getSettingsByUsername(String username) {
        return settingsClient.getSettingsByUsername(username);
    }
}
