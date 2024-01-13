package com.example.restapi.mappers;

import com.example.restapi.dtos.ItemDTO;
import com.example.restapi.entites.Item;

public class ItemMapper {
    public static ItemDTO toModel(Item entity) {
        return ItemDTO.builder()
                .itemName(entity.getItemName())
                .bidPrice(entity.getBidPrice())
                .startPrice(entity.getStartPrice())
                .bids(entity.getBids().stream().map(BidMapper::toModel).toList())
                .placementDate(entity.getPlacementDate())
                .description(entity.getDescription())
                .expirationDate(entity.getExpirationDate())
                .attachments(entity.getAttachments().stream().map(AttachmentMapper::toModel).toList())
                .build();
    }

}
