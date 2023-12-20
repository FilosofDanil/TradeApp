package com.example.tradeapp.builder.director;

import com.example.tradeapp.entities.messages.impl.TextMessage;

import java.util.List;

public interface MessageDirector {
    TextMessage buildTextMessage(Long chatId, String text);

    TextMessage buildTextMessageWithReplyKeyboard(Long chatId, String text, List<String> rows);

    TextMessage buildTextMessageWithInlineKeyboard(Long chatId, String text, List<String> rows);
}
