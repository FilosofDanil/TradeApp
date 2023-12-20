package com.example.tradeapp.services.handlers;

import com.example.tradeapp.entities.session.UserSession;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface Handler {
    void handle(UserSession session, Update update);
}
