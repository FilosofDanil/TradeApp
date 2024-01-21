package com.example.tradeapp.components.impl;

import com.example.tradeapp.components.ChatIdFromUpdateComponent;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class ChatIdFromUpdateComponentImpl implements ChatIdFromUpdateComponent {
    @Override
    public Long getChatIdFromUpdate(Update update) {
        if (update.getMessage() != null) {
            return update.getMessage().getChatId();
        } else {
            return update.getCallbackQuery().getMessage().getChatId();
        }
    }
}
