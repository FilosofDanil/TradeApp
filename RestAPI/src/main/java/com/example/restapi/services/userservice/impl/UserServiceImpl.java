package com.example.restapi.services.userservice.impl;

import com.example.restapi.dtos.TelegramUserDTO;
import com.example.restapi.mappers.UserMapper;
import com.example.restapi.repositories.TelegramUserRepository;
import com.example.restapi.services.userservice.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final TelegramUserRepository userRepo;

    @Override
    public TelegramUserDTO getByUsername(String username) {
        return userRepo.findByUsername(username)
                .map(UserMapper::toModel)
                .get();
    }

    @Override
    public TelegramUserDTO getByTelegramName(String tgName) {
        return userRepo.findByTgName(tgName)
                .map(UserMapper::toModel)
                .get();
    }
}
