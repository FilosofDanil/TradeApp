package com.example.tradeapp.entities.messages.impl;

import com.example.tradeapp.entities.messages.Message;
import lombok.Getter;
import lombok.Setter;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

@Getter
@Setter
public class TextMessage extends Message {
    public TextMessage(Long chatId, String message) {
        super(chatId);
        this.message = message;
    }

    public TextMessage(Long chatId, String message, ReplyKeyboardMarkup replyKeyboard) {
        super(chatId);
        this.message = message;
        this.replyKeyboard = replyKeyboard;
    }

    public TextMessage(Long chatId, String message, InlineKeyboardMarkup inlineKeyboardMarkup) {
        super(chatId);
        this.message = message;
        this.inlineKeyboardMarkup = inlineKeyboardMarkup;
    }

    private String message;
    private ReplyKeyboardMarkup replyKeyboard;
    private InlineKeyboardMarkup inlineKeyboardMarkup;
}
