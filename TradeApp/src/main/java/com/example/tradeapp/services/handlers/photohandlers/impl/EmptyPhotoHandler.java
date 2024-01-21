package com.example.tradeapp.services.handlers.photohandlers.impl;

import com.example.tradeapp.builder.director.MessageDirector;
import com.example.tradeapp.components.ChatIdFromUpdateComponent;
import com.example.tradeapp.components.impl.TextMessageSender;
import com.example.tradeapp.entities.session.UserSession;
import com.example.tradeapp.services.handlers.photohandlers.PhotoHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component("emptyPhotoHandler")
@RequiredArgsConstructor
public class EmptyPhotoHandler implements PhotoHandler {
    private final TextMessageSender textMessageSender;

    private final MessageDirector messageDirector;

    private final ChatIdFromUpdateComponent updateComponent;

    @Override
    public void handle(UserSession session, Update update) {
        Long chatId = updateComponent.getChatIdFromUpdate(update);
        textMessageSender.sendMessage(messageDirector
                .buildTextMessage(chatId, "Нема такого варіанту відповіді! В надсиланні фото зараз немає необхідності."));
    }
}
