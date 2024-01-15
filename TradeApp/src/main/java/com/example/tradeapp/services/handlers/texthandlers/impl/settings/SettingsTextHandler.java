package com.example.tradeapp.services.handlers.texthandlers.impl.settings;

import com.example.tradeapp.builder.director.MessageDirector;
import com.example.tradeapp.components.impl.TextMessageSender;
import com.example.tradeapp.entities.session.UserSession;
import com.example.tradeapp.services.handlers.texthandlers.TextHandler;
import com.example.tradeapp.services.session.SessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component("settings")
@RequiredArgsConstructor
public class SettingsTextHandler implements TextHandler {
    private final TextMessageSender textMessageSender;

    private final MessageDirector messageDirector;

    private final SessionService sessionService;

    @Override
    public void handle(UserSession session, Update update) {

    }
}
