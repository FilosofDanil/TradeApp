package com.example.restapi.controllers;

import com.example.restapi.dtos.TelegramUserDTO;
import com.example.restapi.services.crud.CRUDService;
import com.example.restapi.services.userservice.UserService;
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
    private final CRUDService<TelegramUserDTO> crudService;

    private final UserService userService;

    @GetMapping("")
    public ResponseEntity<List<TelegramUserDTO>> getAllUsers() {
        return ResponseEntity.ok(crudService.getAll());
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<TelegramUserDTO> getUserByUsername(@PathVariable String username) {
        return ResponseEntity.ok(userService.getByUsername(username));
    }

    @GetMapping("/tgName/{tgName}")
    public ResponseEntity<TelegramUserDTO> getUserByTgName(@PathVariable String tgName) {
        return ResponseEntity.ok(userService.getByTelegramName(tgName));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TelegramUserDTO> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(crudService.getById(id));
    }

    @PostMapping("")
    public ResponseEntity<TelegramUserDTO> createUser(@RequestBody TelegramUserDTO user) {
        return new ResponseEntity<>(crudService.create(user), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HttpStatus> updateUser(@RequestBody TelegramUserDTO user, @PathVariable Long id) {
        crudService.update(id, user);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable Long id) {
        crudService.delete(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

}
