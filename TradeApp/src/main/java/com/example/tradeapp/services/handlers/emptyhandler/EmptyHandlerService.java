package com.example.tradeapp.services.handlers.emptyhandler;

import com.example.tradeapp.builder.director.MessageDirector;
import com.example.tradeapp.components.ChatIdFromUpdateComponent;
import com.example.tradeapp.components.impl.TextMessageSender;
import com.example.tradeapp.entities.session.UserSession;
import com.example.tradeapp.services.handlers.Handler;
import com.example.tradeapp.services.handlers.photohandlers.PhotoHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service("emptyHandler")
@RequiredArgsConstructor
public class EmptyHandlerService implements Handler {
    private final TextMessageSender textMessageSender;

    private final MessageDirector messageDirector;

    private final ChatIdFromUpdateComponent updateComponent;

    @Override
    public void handle(UserSession session, Update update) {
        Long chatId = updateComponent.getChatIdFromUpdate(update);
        textMessageSender.sendMessage(messageDirector
                .buildTextMessage(chatId, "Нема такого варіанту відповіді!"));
    }
}
