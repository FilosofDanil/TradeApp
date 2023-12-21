package com.example.tradeapp.services.handlers.texthandlers.impl;

import com.example.tradeapp.builder.director.MessageDirector;
import com.example.tradeapp.components.impl.TextMessageSender;
import com.example.tradeapp.entities.session.UserSession;
import com.example.tradeapp.services.handlers.texthandlers.TextHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component("started")
@RequiredArgsConstructor
public class StartedTextHandler implements TextHandler {
    private final TextMessageSender textMessageSender;

    private final MessageDirector messageDirector;

    @Override
    public void handle(UserSession session, Update update) {
        String text = "Щоб почати роботу з ботом, розпочніть з команди /marketplace. Якщо вам незрозуміло як користуватися системою натисніть /help бо напишіть нам у support";
        textMessageSender.sendMessage(messageDirector
                .buildTextMessage(update.getMessage().getChatId(), text));
    }
}
