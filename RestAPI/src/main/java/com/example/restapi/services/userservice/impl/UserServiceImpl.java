package com.example.restapi.services.userservice.impl;

import com.example.restapi.entites.TelegramUser;
import com.example.restapi.repositories.TelegramUserRepository;
import com.example.restapi.services.userservice.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final TelegramUserRepository userRepo;

    @Override
    public TelegramUser getByUsername(String username) {
        return userRepo.findByUsername(username).get();
    }

    @Override
    public TelegramUser getByTelegramName(String tgName) {
        return userRepo.findByTgName(tgName).get();
    }
}
