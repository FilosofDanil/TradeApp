package com.example.tradeapp.services.statemanager;

import com.example.tradeapp.entities.session.UserSession;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface StateService {
    void handle(UserSession session, Update update);
}
