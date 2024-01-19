package com.example.tradeapp.services.handlers.texthandlers.impl.search;

import com.example.tradeapp.builder.director.MessageDirector;
import com.example.tradeapp.builder.director.PhotoMessageDirector;
import com.example.tradeapp.client.ItemClient;
import com.example.tradeapp.client.SettingsClient;
import com.example.tradeapp.components.ChatIdFromUpdateComponent;
import com.example.tradeapp.components.MessageSender;
import com.example.tradeapp.components.SendArticles;
import com.example.tradeapp.components.SettingsComponent;
import com.example.tradeapp.components.impl.TextMessageSender;
import com.example.tradeapp.entities.messages.impl.PhotoMessage;
import com.example.tradeapp.entities.models.Items;
import com.example.tradeapp.entities.session.UserSession;
import com.example.tradeapp.services.handlers.texthandlers.TextHandler;
import com.example.tradeapp.services.session.SessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component("searching")
@RequiredArgsConstructor
public class SearchArticleTextHandler implements TextHandler {
    private final SendArticles sendArticleComponent;

    private final TextMessageSender textMessageSender;

    private final MessageSender<PhotoMessage> photoMessageSender;

    private final MessageDirector messageDirector;

    private final PhotoMessageDirector photoMessageDirector;

    private final SessionService sessionService;

    private final ItemClient itemClient;

    private final ChatIdFromUpdateComponent updateComponent;

    @Override
    public void handle(UserSession session, Update update) {
        String message = update.getMessage().getText();
        Long chatId = updateComponent.getChatIdFromUpdate(update);
        Map<String, String> data = session.getUserData();
        if (data.get("itemList") == null) {
            sendArticleComponent.sendArticlesToUser(update, session);
            data.put("currentId", "0");
            sessionService.updateSession(chatId, session);
        }
        List<Long> itemIds = Arrays.stream(data.get("itemList")
                        .split(" "))
                .map(Long::parseLong)
                .toList();
        int currentId = getCurrentId(data);
        Items item = itemClient.getItemById(itemIds.get(currentId));
        currentId++;
        photoMessageSender.sendMessage(photoMessageDirector
                .buildPhotoMessage(chatId, item));
        if (itemIds.size() <= currentId) {
            session.setUserData(new HashMap<>(Map.of("", "")));
        }

    }

    private Integer getCurrentId(Map<String, String> data) {
        return Integer.parseInt(data.get("currentId"));
    }
}
