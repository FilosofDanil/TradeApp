package com.example.restapi.controllers;

import com.example.restapi.dtos.TelegramUserDTO;
import com.example.restapi.entites.TelegramUser;
import com.example.restapi.services.crud.CRUDService;
import com.example.restapi.services.userservice.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final CRUDService<TelegramUser> crudService;

    private final UserService userService;

    private final ModelMapper modelMapper;

    @GetMapping("")
    public ResponseEntity<List<TelegramUserDTO>> getAllUsers() {
        return ResponseEntity.ok(crudService
                .getAll()
                .stream()
                .map(this::toDto)
                .toList());
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<TelegramUserDTO> getUserByUsername(@PathVariable String username) {
        return ResponseEntity.ok(toDto(userService.getByUsername(username)));
    }

    @GetMapping("/tgName/{tgName}")
    public ResponseEntity<TelegramUserDTO> getUserByTgName(@PathVariable String tgName) {
        return ResponseEntity.ok(toDto(userService.getByTelegramName(tgName)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TelegramUserDTO> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(toDto(crudService.getById(id)));
    }

    @PostMapping("")
    public ResponseEntity<TelegramUserDTO> createUser(@RequestBody TelegramUserDTO user) {
        return new ResponseEntity<>(toDto(crudService.create(toEntity(user))), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HttpStatus> updateUser(@RequestBody TelegramUserDTO user, @PathVariable Long id) {
        crudService.update(id, toEntity(user));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable Long id) {
        crudService.delete(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    private TelegramUserDTO toDto(TelegramUser telegramUser) {
        return modelMapper.map(telegramUser, TelegramUserDTO.class);
    }

    private TelegramUser toEntity(TelegramUserDTO telegramUserDTO) {
        return modelMapper.map(telegramUserDTO, TelegramUser.class);
    }
}
