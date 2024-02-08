package com.example.tradeapp.services.handlers.queryhandler.articles;

import com.example.tradeapp.builder.director.MessageDirector;
import com.example.tradeapp.builder.director.PhotoMessageDirector;
import com.example.tradeapp.client.ItemClient;
import com.example.tradeapp.components.ChatIdFromUpdateComponent;
import com.example.tradeapp.components.MessageSender;
import com.example.tradeapp.components.impl.TextMessageSender;
import com.example.tradeapp.entities.messages.impl.PhotoMessage;
import com.example.tradeapp.entities.models.Items;
import com.example.tradeapp.entities.session.UserSession;
import com.example.tradeapp.services.handlers.queryhandler.QueryHandler;
import com.example.tradeapp.services.session.SessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;
import java.util.Map;

@Component("myItems")
@RequiredArgsConstructor
public class ShowArticleQueryHandler implements QueryHandler {
    private final TextMessageSender textMessageSender;

    private final MessageDirector messageDirector;

    private final SessionService sessionService;

    private final ChatIdFromUpdateComponent updateComponent;

    private final ItemClient itemClient;

    private final MessageSender<PhotoMessage> photoMessageSender;

    private final PhotoMessageDirector photoMessageDirector;

    @Override
    public void handle(UserSession session, Update update) {
        String message = update.getCallbackQuery().getData();
        Long chatId = updateComponent.getChatIdFromUpdate(update);
        Long itemId = Long.parseLong(message);
        Map<String, String> data = session.getUserData();
        data.put("itemId", itemId.toString());
        session.setUserData(data);
        Items item = itemClient.getItemById(itemId);
        List<String> rows = List.of("Подовжити термін продажу", "Переглянути список охочих купити товар", "Видалити товар");
        textMessageSender.sendMessage(messageDirector
                .buildTextMessage(chatId, "Ось ваш товар:"));
        photoMessageSender.sendMessage(photoMessageDirector
                .buildPhotoMessage(chatId, item, rows));
        session.setHandler("itemMenu");
        sessionService.updateSession(chatId, session);
    }

    @Override
    public String getCallbackQuery() {
        return "";
    }

    @Override
    public boolean isNumeric() {
        return true;
    }
}
