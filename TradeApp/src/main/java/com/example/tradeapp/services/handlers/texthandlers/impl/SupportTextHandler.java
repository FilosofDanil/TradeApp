package com.example.tradeapp.services.handlers.texthandlers.impl;

import com.example.tradeapp.builder.director.MessageDirector;
import com.example.tradeapp.components.impl.TextMessageSender;
import com.example.tradeapp.entities.session.UserSession;
import com.example.tradeapp.services.handlers.texthandlers.TextHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component("support")
@RequiredArgsConstructor
public class SupportTextHandler implements TextHandler {
    private final TextMessageSender textMessageSender;

    private final MessageDirector messageDirector;

    @Override
    public void handle(UserSession session, Update update) {
        String text = "Ваше повідомлення надіслано, очікуйте на відповідь...";
        textMessageSender.sendMessage(messageDirector
                .buildTextMessage(update.getMessage().getChatId(), text));
        textMessageSender.sendMessage(messageDirector
                .buildTextMessage(891477091L, update.getMessage().getText()));
    }
}
