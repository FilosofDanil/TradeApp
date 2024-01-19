package com.example.tradeapp.components;

import com.example.tradeapp.entities.models.Settings;
import com.example.tradeapp.entities.session.UserSession;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface SendArticles {
    void sendArticlesToUser(Update update, UserSession session);
}
