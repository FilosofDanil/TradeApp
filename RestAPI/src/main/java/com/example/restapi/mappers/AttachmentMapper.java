package com.example.restapi.mappers;

import com.example.restapi.dtos.AttachmentDTO;
import com.example.restapi.dtos.ItemDTO;
import com.example.restapi.entites.Attachment;
import com.example.restapi.entites.Item;

public class AttachmentMapper {
    public static AttachmentDTO toModel(Attachment entity) {
        return AttachmentDTO.builder()
                .itemData(entity.getItemData())
                .itemType(entity.getItemType())
                .itemId(entity.getItem().getId())
                .build();
    }
}
