package com.example.tradeapp.services.handlers.texthandlers.impl.articles;

import com.example.tradeapp.builder.director.MessageDirector;
import com.example.tradeapp.client.AttachmentClient;
import com.example.tradeapp.client.ItemClient;
import com.example.tradeapp.components.*;
import com.example.tradeapp.components.impl.TextMessageSender;
import com.example.tradeapp.entities.models.Attachments;
import com.example.tradeapp.entities.models.Items;
import com.example.tradeapp.entities.session.UserSession;
import com.example.tradeapp.services.handlers.texthandlers.TextHandler;
import com.example.tradeapp.services.session.SessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.File;

import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component("addArticle")
@RequiredArgsConstructor
public class AddArticleTextHandler implements TextHandler {
    private final TextMessageSender textMessageSender;

    private final MessageDirector messageDirector;

    private final SessionService sessionService;

    private final ChatIdFromUpdateComponent updateComponent;

    private final ItemClient itemClient;

    private final AttachmentComponent attachmentComponent;

    private final FileDeleter fileDeleter;

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
            Long itemId = Long.parseLong(data.get("itemId"));
            attachmentComponent.getAllAttachments(itemId)
                    .forEach(fileDeleter::deleteFile);
            itemClient.deleteItem(itemId);
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
