package com.example.restapi.repositories;

import com.example.restapi.entites.Settings;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SettingsRepository extends CrudRepository<Settings, Long> {
    List<Settings> findAll();

    @Query(value = "select s.id, s.city, s.user_id from public.settings s\n" +
            "    join public.users u on u.username = :username", nativeQuery = true)
    Settings findByUserName(@Param("username") String username);
}
