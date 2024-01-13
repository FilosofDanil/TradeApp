package com.example.restapi.services.userservice;

import com.example.restapi.dtos.TelegramUserDTO;

public interface UserService {
    TelegramUserDTO getByUsername(String username);

    TelegramUserDTO getByTelegramName(String tgName);
}
