package com.example.tradeapp.client;

import com.example.tradeapp.entities.models.Settings;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "settingsClient", url = "${application.config.settings}")
public interface SettingsClient {
    @GetMapping("")
    List<Settings> getAllSettings();

    @GetMapping("/{id}")
    Settings getSettingsById(@PathVariable("id") Long id);

    @GetMapping("/user/{username}")
    Settings getSettingsByUsername(@PathVariable("username") String username);

    @PostMapping("")
    Settings createSettings(@RequestBody Settings settingsDTO);

    @PutMapping("/{id}")
    void updateSettings(@PathVariable("id") Long id, @RequestBody Settings settingsDTO);

    @DeleteMapping("/{id}")
    void deleteSettings(@PathVariable("id") Long id);
}
