package com.example.tradeapp.components;

import com.example.tradeapp.entities.session.UserSession;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface StartSearchingComponent {
    void startSearchingProcess(Update update, UserSession session);
}
