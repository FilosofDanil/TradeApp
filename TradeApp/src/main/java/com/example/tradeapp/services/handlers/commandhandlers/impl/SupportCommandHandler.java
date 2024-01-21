package com.example.tradeapp.services.handlers.commandhandlers.impl;

import com.example.tradeapp.builder.director.MessageDirector;
import com.example.tradeapp.components.ChatIdFromUpdateComponent;
import com.example.tradeapp.components.MessageSender;
import com.example.tradeapp.entities.messages.impl.TextMessage;
import com.example.tradeapp.entities.session.UserSession;
import com.example.tradeapp.services.handlers.commandhandlers.CommandHandler;
import com.example.tradeapp.services.session.SessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@RequiredArgsConstructor
public class SupportCommandHandler implements CommandHandler {
    private final static String command = "/support";

    private final MessageSender<TextMessage> textMessageMessageSender;

    private final MessageDirector messageDirector;

    private final SessionService sessionService;

    private final ChatIdFromUpdateComponent updateComponent;

    @Override
    public void handle(UserSession session, Update update) {
        String text = "Напишіть ваше повідомлення для підтримки!";
        Long chatId = updateComponent.getChatIdFromUpdate(update);
        textMessageMessageSender.sendMessage(messageDirector
                .buildTextMessage(chatId, text));
        session.setHandler("support");
        sessionService.updateSession(chatId, session);
    }

    @Override
    public String getCommand() {
        return command;
    }
}
