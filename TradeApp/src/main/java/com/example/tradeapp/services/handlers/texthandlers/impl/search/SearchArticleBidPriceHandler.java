package com.example.tradeapp.services.handlers.texthandlers.impl.search;

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

@Component("bidPrice")
@RequiredArgsConstructor
public class SearchArticleBidPriceHandler implements TextHandler {
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
            data.put("bidPrice", price.toString());
            session.setHandler("bidComment");
            session.setUserData(data);
            text += "Надішліть невеликий коментар(не більше речення)";
        } catch (NumberFormatException e) {
            session.setHandler("bidPrice");
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
