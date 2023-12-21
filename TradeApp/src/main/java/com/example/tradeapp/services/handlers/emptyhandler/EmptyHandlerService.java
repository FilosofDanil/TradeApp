package com.example.tradeapp.services.handlers.emptyhandler;

import com.example.tradeapp.builder.director.MessageDirector;
import com.example.tradeapp.components.impl.TextMessageSender;
import com.example.tradeapp.entities.session.UserSession;
import com.example.tradeapp.services.handlers.Handler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service("emptyHandler")
@RequiredArgsConstructor
public class EmptyHandlerService implements Handler {
    private final TextMessageSender textMessageSender;

    private final MessageDirector messageDirector;

    @Override
    public void handle(UserSession session, Update update) {
        String text = "Нема такого варіанту відповіді!";
        textMessageSender.sendMessage(messageDirector
                .buildTextMessage(update.getMessage().getChatId(), text));
    }
}
