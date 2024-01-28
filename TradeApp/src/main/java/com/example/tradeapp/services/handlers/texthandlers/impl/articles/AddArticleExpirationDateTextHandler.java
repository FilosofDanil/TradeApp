package com.example.tradeapp.services.handlers.texthandlers.impl.articles;

import com.example.tradeapp.builder.director.MessageDirector;
import com.example.tradeapp.builder.director.PhotoMessageDirector;
import com.example.tradeapp.client.ItemClient;
import com.example.tradeapp.client.ItemTypeClient;
import com.example.tradeapp.components.*;
import com.example.tradeapp.components.impl.TextMessageSender;
import com.example.tradeapp.entities.messages.impl.PhotoMessage;
import com.example.tradeapp.entities.models.ItemType;
import com.example.tradeapp.entities.models.Items;
import com.example.tradeapp.entities.session.UserSession;
import com.example.tradeapp.services.handlers.texthandlers.TextHandler;
import com.example.tradeapp.services.session.SessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;
import java.util.Map;

@Component("articleAddDate")
@RequiredArgsConstructor
public class AddArticleExpirationDateTextHandler implements TextHandler {
    private final TextMessageSender textMessageSender;

    private final MessageSender<PhotoMessage> photoMessageSender;

    private final MessageDirector messageDirector;

    private final PhotoMessageDirector photoMessageDirector;

    private final ItemFormer itemFormer;

    private final SessionService sessionService;

    private final ChatIdFromUpdateComponent updateComponent;

    private final UserComponent userComponent;

    private final ItemClient itemClient;

    private final AttachmentComponent attachmentComponent;

    @Override
    public void handle(UserSession session, Update update) {
        String message = update.getMessage().getText();
        String username = userComponent.getUsernameFromMessage(update);
        Long chatId = updateComponent.getChatIdFromUpdate(update);
        Map<String, String> data = session.getUserData();
        data.put("articleDate", message);
        List<String> rows = List.of("\uD83D\uDC4D\uD83C\uDFFB", "\uD83D\uDC4E\uD83C\uDFFB");
        sessionService.updateSession(chatId, session);
        textMessageSender.sendMessage(messageDirector
                .buildTextMessage(chatId, "Добре! Ось ваш товар:"));
        //here send item with photo
        Items item = itemFormer.formItem(data, username);
        Items createdItem = itemClient.createItem(item);
        attachmentComponent.createAttachment(data, createdItem.getId());
        photoMessageSender.sendMessage(photoMessageDirector
                .buildPhotoMessage(chatId, createdItem));
        session.setHandler("addArticle");
        sessionService.updateSession(chatId, session);
        textMessageSender.sendMessage(messageDirector
                .buildTextMessageWithReplyKeyboard(chatId, "Додати товар?", rows));
    }
}
