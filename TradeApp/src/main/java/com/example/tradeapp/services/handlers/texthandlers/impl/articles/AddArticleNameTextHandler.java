package com.example.tradeapp.services.handlers.texthandlers.impl.articles;

import com.example.tradeapp.builder.director.MessageDirector;
import com.example.tradeapp.components.ChatIdFromUpdateComponent;
import com.example.tradeapp.components.impl.TextMessageSender;
import com.example.tradeapp.entities.session.UserSession;
import com.example.tradeapp.services.handlers.texthandlers.TextHandler;
import com.example.tradeapp.services.session.SessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Map;

@Component("addArticleName")
@RequiredArgsConstructor
public class AddArticleNameTextHandler implements TextHandler {
    private final TextMessageSender textMessageSender;

    private final MessageDirector messageDirector;

    private final SessionService sessionService;

    private final ChatIdFromUpdateComponent updateComponent;

    @Override
    public void handle(UserSession session, Update update) {
        String text = "";
        String message = update.getMessage().getText();
        Long chatId = updateComponent.getChatIdFromUpdate(update);
        Map<String, String> data = session.getUserData();
        if (message.length() >= 6) {
            session.setHandler("addArticleDescription");
            data.put("articleName", message);
            session.setUserData(data);
            text+="Добре! Далі коротко опишіть товар!";
        } else {
            session.setHandler("addArticleName");
            text += "Назва закоротка! Введіть ще раз";
        }
        sessionService.updateSession(chatId, session);
        textMessageSender.sendMessage(messageDirector
                .buildTextMessage(chatId, text));
    }
}
