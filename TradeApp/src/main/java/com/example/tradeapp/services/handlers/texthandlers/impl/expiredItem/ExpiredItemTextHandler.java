package com.example.tradeapp.services.handlers.texthandlers.impl.expiredItem;

import com.example.tradeapp.builder.director.MessageDirector;
import com.example.tradeapp.builder.director.PhotoMessageDirector;
import com.example.tradeapp.client.BidClient;
import com.example.tradeapp.client.ItemClient;
import com.example.tradeapp.components.ChatIdFromUpdateComponent;
import com.example.tradeapp.components.ItemFormer;
import com.example.tradeapp.components.MessageSender;
import com.example.tradeapp.components.impl.TextMessageSender;
import com.example.tradeapp.entities.messages.impl.PhotoMessage;
import com.example.tradeapp.entities.models.Items;
import com.example.tradeapp.entities.session.UserSession;
import com.example.tradeapp.services.handlers.texthandlers.TextHandler;
import com.example.tradeapp.services.session.SessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;
import java.util.Map;

@Component("expiredArticleHandler")
@RequiredArgsConstructor
public class ExpiredItemTextHandler implements TextHandler {
    private final TextMessageSender textMessageSender;

    private final MessageDirector messageDirector;

    private final SessionService sessionService;

    private final ChatIdFromUpdateComponent updateComponent;

    private final ItemClient itemClient;

    private final BidClient bidClient;

    private final MessageSender<PhotoMessage> photoMessageSender;

    private final PhotoMessageDirector photoMessageDirector;

    @Override
    public void handle(UserSession session, Update update) {
        String text = "";
        String message = update.getMessage().getText();
        Long chatId = updateComponent.getChatIdFromUpdate(update);
        Map<String, String> data = session.getUserData();
        if (message.equals("\uD83D\uDC4D\uD83C\uDFFB")) {
            Long itemId = Long.parseLong(data.get("itemId"));
            Items item = itemClient.getItemById(itemId);
            text+="Ось ваш товар:";
            List<String> rows = List.of("Подовжити термін продажу", "Переглянути список охочих купити товар", "Видалити товар");
            textMessageSender.sendMessage(messageDirector
                    .buildTextMessage(chatId, text));
            photoMessageSender.sendMessage(photoMessageDirector
                    .buildPhotoMessage(chatId, item, rows));
        } else {
            session.setHandler("expiredArticleHandler");
            text += "Немає такого варіанту відповіді!";
            textMessageSender.sendMessage(messageDirector
                    .buildTextMessage(chatId, text));
        }
        sessionService.updateSession(chatId, session);

    }
}
