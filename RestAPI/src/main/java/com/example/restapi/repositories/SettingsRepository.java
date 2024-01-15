package com.example.restapi.repositories;

import com.example.restapi.entites.Settings;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SettingsRepository extends CrudRepository<Settings, Long> {
    List<Settings> findAll();
}
