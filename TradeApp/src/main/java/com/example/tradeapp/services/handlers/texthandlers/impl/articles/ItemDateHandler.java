package com.example.tradeapp.services.handlers.texthandlers.impl.articles;

import com.example.tradeapp.builder.director.MessageDirector;
import com.example.tradeapp.client.ItemClient;
import com.example.tradeapp.components.ChatIdFromUpdateComponent;
import com.example.tradeapp.components.impl.TextMessageSender;
import com.example.tradeapp.entities.models.Items;
import com.example.tradeapp.entities.session.UserSession;
import com.example.tradeapp.services.handlers.texthandlers.TextHandler;
import com.example.tradeapp.services.session.SessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@Component("itemExtend")
@RequiredArgsConstructor
public class ItemDateHandler implements TextHandler {
    private final TextMessageSender textMessageSender;

    private final MessageDirector messageDirector;

    private final SessionService sessionService;

    private final ChatIdFromUpdateComponent updateComponent;

    private final ItemClient itemClient;

    @Override
    public void handle(UserSession session, Update update) {
        Long chatId = updateComponent.getChatIdFromUpdate(update);
        Map<String, String> data = session.getUserData();
        try {
            String message = update.getMessage().getText();
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
            Date date = formatter.parse(message);
            Long itemId = Long.parseLong(data.get("itemId"));
            Items item = itemClient.getItemById(itemId);
            item.setExpired(false);
            item.setExpirationDate(date);
            itemClient.updateItem(item, itemId);
            sessionService.updateSession(chatId, session);
            textMessageSender.sendMessage(messageDirector
                    .buildTextMessage(chatId, "Дату успішно змінено!"));
        } catch (ParseException e){
            sessionService.updateSession(chatId, session);
            session.setHandler("itemExtend");
            textMessageSender.sendMessage(messageDirector
                    .buildTextMessage(chatId, "Введіть дату заново!"));
        }

    }
}
