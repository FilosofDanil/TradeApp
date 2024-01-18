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

@Component("addArticlePrice")
@RequiredArgsConstructor
public class AddArticleStartPriceTextHandler implements TextHandler {
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
        try {
            Integer price = checkPrice(message);
            data.put("articlePrice", price.toString());
            data.put("articlePhotos", "0");
            session.setHandler("articleAddPhotos");
            session.setUserData(data);
            text += "Надішліть фото(не більше 3-х) вашого товару";
        } catch (NumberFormatException e) {
            session.setHandler("addArticlePrice");
            text += "Неправильно введена ціна. Вкажіть ціну товару в цілому числі(в гривнях)";
        }
        sessionService.updateSession(chatId, session);
        textMessageSender.sendMessage(messageDirector
                .buildTextMessage(chatId, text));
    }

    private Integer checkPrice(String message) throws NumberFormatException {
        return Integer.parseInt(message);
    }
}
