package com.example.tradeapp.services.handlers.texthandlers.impl.articles;

import com.example.tradeapp.builder.director.MessageDirector;
import com.example.tradeapp.builder.director.PhotoMessageDirector;
import com.example.tradeapp.client.ItemClient;
import com.example.tradeapp.components.ChatIdFromUpdateComponent;
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

@Component("confirmDeleting")
@RequiredArgsConstructor
public class ConfirmDeletingTextHandler implements TextHandler {
    private final TextMessageSender textMessageSender;

    private final MessageDirector messageDirector;

    private final SessionService sessionService;

    private final ChatIdFromUpdateComponent updateComponent;

    private final ItemClient itemClient;

    private final MessageSender<PhotoMessage> photoMessageSender;

    private final PhotoMessageDirector photoMessageDirector;

    @Override
    public void handle(UserSession session, Update update) {
        String text = "";
        String message = update.getMessage().getText();
        Long chatId = updateComponent.getChatIdFromUpdate(update);
        Map<String, String> data = session.getUserData();
        Long itemId = Long.parseLong(data.get("itemId"));
        if(message.equals("Так")){
            itemClient.deleteItem(itemId);
            text+="Ваш товар успішно видалено з системи!";
        } else if(message.equals("Ні")){
            text+="Повертаємося до меню!";
            Items item = itemClient.getItemById(itemId);
            text+="Ось ваш товар:";
            List<String> rows = List.of("Подовжити термін продажу", "Переглянути список охочих купити товар", "Видалити товар");
            textMessageSender.sendMessage(messageDirector
                    .buildTextMessage(chatId, text));
            photoMessageSender.sendMessage(photoMessageDirector
                    .buildPhotoMessage(chatId, item, rows));
            session.setHandler("itemMenu");
            textMessageSender.sendMessage(messageDirector
                    .buildTextMessageWithReplyKeyboard(chatId, text, rows));
        } else {
            session.setHandler("confirmDeleting");
            text+="Нема такого варіанту відповіді!";
        }
        sessionService.updateSession(chatId, session);
        textMessageSender.sendMessage(messageDirector
                .buildTextMessage(chatId, text));
    }
}
