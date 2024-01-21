package com.example.restapi.services.userservice;

import com.example.restapi.dtos.TelegramUserDTO;
import com.example.restapi.entites.TelegramUser;

public interface UserService {
    TelegramUser getByUsername(String username);

    TelegramUser getByTelegramName(String tgName);

    Boolean checkByUsername(String username);

    Boolean checkByTgName(String tgName);
}
