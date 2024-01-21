package com.example.tradeapp.builder.director;

import com.example.tradeapp.entities.messages.impl.PhotoMessage;
import com.example.tradeapp.entities.messages.impl.TextMessage;
import com.example.tradeapp.entities.models.Items;

import java.util.List;

public interface PhotoMessageDirector {
    PhotoMessage buildPhotoMessage(Long chatId, Items items);

    PhotoMessage buildPhotoMessage(Long chatId, Items items, List<String> rows);
}
