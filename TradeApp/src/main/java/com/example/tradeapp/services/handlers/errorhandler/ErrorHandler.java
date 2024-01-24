package com.example.tradeapp.services.handlers.errorhandler;

import com.example.tradeapp.builder.director.MessageDirector;
import com.example.tradeapp.components.ChatIdFromUpdateComponent;
import com.example.tradeapp.components.impl.TextMessageSender;
import com.example.tradeapp.entities.session.UserSession;
import com.example.tradeapp.services.handlers.Handler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service("errorHandler")
@RequiredArgsConstructor
public class ErrorHandler implements Handler {

    private final TextMessageSender textMessageSender;

    private final MessageDirector messageDirector;

    private final ChatIdFromUpdateComponent updateComponent;

    @Override
    public void handle(UserSession session, Update update) {
        Long chatId = updateComponent.getChatIdFromUpdate(update);
        textMessageSender.sendMessage(messageDirector
                .buildTextMessage(chatId, "При обробці запиту виникла помилка! Будь-ласка ознайомтеся з правилами або повідомте про несправність в підтримці /support"));
    }
}
