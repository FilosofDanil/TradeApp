package com.example.tradeapp.builder.director.impl;

import com.example.tradeapp.builder.director.KeyboardBuilder;
import com.example.tradeapp.builder.director.PhotoMessageDirector;
import com.example.tradeapp.client.ItemTypeClient;
import com.example.tradeapp.entities.messages.impl.PhotoMessage;
import com.example.tradeapp.entities.models.ItemType;
import com.example.tradeapp.entities.models.Items;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PhotoMessageDirectorImpl implements PhotoMessageDirector {
    private final static String LOCAL_STORAGE_PATH = "TradeApp/src/main/resources/photos/";

    private final ItemTypeClient itemTypeClient;

    private final KeyboardBuilder keyboardBuilder;

    @Override
    public PhotoMessage buildPhotoMessage(Long chatId, Items items) {
        String caption = formCaption(items);
        return new PhotoMessage(chatId, LOCAL_STORAGE_PATH + "file_0.jpg", caption);
    }

    @Override
    public PhotoMessage buildPhotoMessage(Long chatId, Items items, List<String> rows) {
        ReplyKeyboardMarkup markup = keyboardBuilder
                .buildReplyKeyboard(rows);
        String caption = formCaption(items);
        return new PhotoMessage(chatId, LOCAL_STORAGE_PATH + "file_0.jpg", caption, markup);
    }

    private String formCaption(Items item) {
        String caption = item.getItemName();
        caption += "\n\n";
        caption += "Категорія: ";
        ItemType itemType = itemTypeClient
                .getAllItemTypes()
                .stream()
                .filter(filteredItemType -> filteredItemType.getId().equals(item.getItemTypeId()))
                .findFirst()
                .get();
        caption += itemType.getName();
        caption += "\n\nОпис товару:\n";
        caption += item.getDescription();
        caption += "\n\nСтартова ціна: " + item.getStartPrice();
        caption += "\nОстання ціна(остання ставка):" + item.getBidPrice();
        caption += "\n\nДата розміщення:" + item.getPlacementDate();
        caption += "\nДата експірації:" + item.getExpirationDate();
        return caption;
    }
}
