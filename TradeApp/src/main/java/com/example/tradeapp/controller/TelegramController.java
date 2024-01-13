package com.example.tradeapp.controller;

import com.example.tradeapp.configs.TelegramBotConfiguration;
import com.example.tradeapp.entities.session.UserSession;
import com.example.tradeapp.services.session.SessionService;
import com.example.tradeapp.services.statemanager.StateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Controller
@RequiredArgsConstructor
public class TelegramController extends TelegramLongPollingBot {
    private final TelegramBotConfiguration telegramBotConfiguration;

    private final SessionService sessionService;

    private final StateService stateService;

    @Override
    public String getBotUsername() {
        return telegramBotConfiguration.getName();
    }

    @Override
    public String getBotToken() {
        return telegramBotConfiguration.getToken();
    }

    @Override
    public void onUpdateReceived(Update update) {
        Message message;
        if (update.hasCallbackQuery()){
            message = update.getCallbackQuery().getMessage();
        } else {
            message = update.getMessage();
        }
        UserSession session = sessionService.getSession(message.getChatId());
        stateService.handle(session, update);
    }
}
