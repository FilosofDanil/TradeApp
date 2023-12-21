package com.example.restapi.controllers;

import com.example.restapi.dtos.TelegramUserDTO;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
public class UserController {
    @GetMapping("")
    public ResponseEntity<List<TelegramUserDTO>> getAllUsers() {
        return null;
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<TelegramUserDTO> getUserByUsername(@PathVariable String username) {
        return null;
    }

    @GetMapping("/tgName/{tgName}")
    public ResponseEntity<TelegramUserDTO> getUserByTgName(@PathVariable String tgName) {
        return null;
    }

    @GetMapping("/{id}")
    public ResponseEntity<TelegramUserDTO> getUserById(@PathVariable Long id) {
        return null;
    }

    @PostMapping("")
    public ResponseEntity<TelegramUserDTO> createUser(@RequestBody TelegramUserDTO user) {
        return null;
    }

    @PutMapping("/{id}")
    public ResponseEntity<HttpStatus> updateUser(@RequestBody TelegramUserDTO user, @PathVariable Long id) {
        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable Long id) {
        return null;
    }

}
