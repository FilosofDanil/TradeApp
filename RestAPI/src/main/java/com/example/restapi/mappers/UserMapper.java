package com.example.restapi.mappers;

import com.example.restapi.dtos.TelegramUserDTO;
import com.example.restapi.entites.TelegramUser;

public class UserMapper {
    public static TelegramUserDTO toModel(TelegramUser entity) {
        return TelegramUserDTO.builder()
                .chatId(entity.getChatId())
                .tgName(entity.getTgName())
                .username(entity.getUsername())
                .bids(entity.getBids().stream().map(BidMapper::toModel).toList())
                .items(entity.getItems().stream().map(ItemMapper::toModel).toList())
                .build();
    }
}
