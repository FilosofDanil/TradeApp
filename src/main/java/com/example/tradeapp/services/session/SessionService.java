package com.example.tradeapp.services.session;

import com.example.tradeapp.entities.UserSession;

public interface SessionService {
    UserSession getSession(Long chatId);

    void updateSession(Long chatId, UserSession session);

    void deleteSession(Long chatId);
}
