package com.example.restapi.mappers;

import com.example.restapi.dtos.BidDTO;
import com.example.restapi.dtos.TelegramUserDTO;
import com.example.restapi.entites.Bid;
import com.example.restapi.entites.TelegramUser;

public class BidMapper {
    public static BidDTO toModel(Bid entity) {
        return BidDTO.builder()
                .bidPrice(entity.getBidPrice())
                .comment(entity.getComment())
                .itemId(entity.getItem().getId())
                .userId(entity.getUser().getId())
                .build();
    }
}
