package com.example.tradeapp.entities.messages.impl;

import com.example.tradeapp.entities.messages.Message;
import lombok.Getter;
import lombok.Setter;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

@Setter
@Getter
public class PhotoMessage extends Message {
    public PhotoMessage(Long chatId, String path, String caption) {
        super(chatId);
        this.path = path;
        this.caption = caption;
    }

    public PhotoMessage(Long chatId, String path, String caption, ReplyKeyboardMarkup markup) {
        super(chatId);
        this.path = path;
        this.caption = caption;
        this.markup = markup;
    }

    private String path;
    private String caption;
    private ReplyKeyboardMarkup markup;
}
