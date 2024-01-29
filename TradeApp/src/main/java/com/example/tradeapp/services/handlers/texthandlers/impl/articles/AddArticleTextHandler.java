package com.example.tradeapp.services.handlers.texthandlers.impl.articles;

import com.example.tradeapp.builder.director.MessageDirector;
import com.example.tradeapp.client.AttachmentClient;
import com.example.tradeapp.client.ItemClient;
import com.example.tradeapp.components.AttachmentComponent;
import com.example.tradeapp.components.ChatIdFromUpdateComponent;
import com.example.tradeapp.components.ItemFormer;
import com.example.tradeapp.components.UserComponent;
import com.example.tradeapp.components.impl.TextMessageSender;
import com.example.tradeapp.entities.models.Items;
import com.example.tradeapp.entities.session.UserSession;
import com.example.tradeapp.services.handlers.texthandlers.TextHandler;
import com.example.tradeapp.services.session.SessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.HashMap;
import java.util.Map;

@Component("addArticle")
@RequiredArgsConstructor
public class AddArticleTextHandler implements TextHandler {
    private final TextMessageSender textMessageSender;

    private final MessageDirector messageDirector;

    private final SessionService sessionService;

    private final ChatIdFromUpdateComponent updateComponent;

    private final ItemClient itemClient;

    private final UserComponent userComponent;

    @Override
    public void handle(UserSession session, Update update) {
        String text = "";
        String message = update.getMessage().getText();
        Long chatId = updateComponent.getChatIdFromUpdate(update);
        Map<String, String> data = session.getUserData();
        if (message.equals("\uD83D\uDC4D\uD83C\uDFFB")) {
            session.setUserData(new HashMap<>(Map.of("", "")));
            session.setHandler("market");
            text += "Ваш товар успішно додано!";
        } else if (message.equals("\uD83D\uDC4E\uD83C\uDFFB")) {
            itemClient.deleteItem(Long.parseLong(data.get("itemId")));
            session.setUserData(new HashMap<>(Map.of("", "")));
            session.setHandler("market");
            text += "Назад до маркетплейсу...";
        } else {
            session.setHandler("addArticle");
            text += "Немає такого варіанту відповіді!";
        }
        sessionService.updateSession(chatId, session);
        textMessageSender.sendMessage(messageDirector
                .buildTextMessage(chatId, text));
    }
}
