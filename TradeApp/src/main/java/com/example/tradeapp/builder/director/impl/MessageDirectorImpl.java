package com.example.tradeapp.builder.director.impl;

import com.example.tradeapp.builder.director.MessageDirector;
import com.example.tradeapp.entities.messages.impl.TextMessage;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MessageDirectorImpl implements MessageDirector {
    @Override
    public TextMessage buildTextMessage(Long chatId, String text) {
        return new TextMessage(chatId, text);
    }

    @Override
    public TextMessage buildTextMessageWithReplyKeyboard(Long chatId, String text, List<String> rows) {
        return null;
    }

    @Override
    public TextMessage buildTextMessageWithInlineKeyboard(Long chatId, String text, List<String> rows) {
        return null;
    }
}
