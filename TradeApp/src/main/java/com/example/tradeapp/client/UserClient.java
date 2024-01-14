package com.example.tradeapp.client;

import com.example.tradeapp.entities.models.Users;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "userClient", url = "${application.config.users}")
public interface UserClient {
    @GetMapping("")
    List<Users> getAll();

    @GetMapping("/username/{username}")
    Users getUserByUsername(@PathVariable String username);

    @GetMapping("/tgName/{tgName}")
    Users getUserByTgName(@PathVariable String tgName);

    @GetMapping("/{id}")
    Users getUserById(@PathVariable Long id);

    @PostMapping("")
    Users createUser(@RequestBody Users user);

    @PutMapping("/{id}")
    void updateUser(@RequestBody Users user, @PathVariable Long id);

    @DeleteMapping("/{id}")
    void deleteUser(@PathVariable Long id);
}
