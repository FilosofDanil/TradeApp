package com.example.tradeapp.services.handlers.commandhandlers.impl;

import com.example.tradeapp.builder.director.MessageDirector;
import com.example.tradeapp.components.ChatIdFromUpdateComponent;
import com.example.tradeapp.components.MessageSender;
import com.example.tradeapp.components.StartSearchingComponent;
import com.example.tradeapp.entities.messages.impl.TextMessage;
import com.example.tradeapp.entities.session.UserSession;
import com.example.tradeapp.services.handlers.commandhandlers.CommandHandler;
import com.example.tradeapp.services.session.SessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class SearchCommandHandler implements CommandHandler {
    private final static String command = "/search";

    private final StartSearchingComponent searchingComponent;

    @Override
    public void handle(UserSession session, Update update) {
        searchingComponent.startSearchingProcess(update, session);
    }

    @Override
    public String getCommand() {
        return command;
    }
}
