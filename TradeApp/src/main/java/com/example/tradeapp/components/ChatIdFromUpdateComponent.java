package com.example.tradeapp.components;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface ChatIdFromUpdateComponent {
    Long getChatIdFromUpdate(Update update);
}
