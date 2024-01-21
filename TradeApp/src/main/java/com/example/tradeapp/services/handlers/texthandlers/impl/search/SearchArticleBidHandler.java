package com.example.tradeapp.services.handlers.texthandlers.impl.search;

import com.example.tradeapp.builder.director.MessageDirector;
import com.example.tradeapp.client.BidClient;
import com.example.tradeapp.client.UserClient;
import com.example.tradeapp.components.BidFormer;
import com.example.tradeapp.components.ChatIdFromUpdateComponent;
import com.example.tradeapp.components.UserComponent;
import com.example.tradeapp.components.impl.TextMessageSender;
import com.example.tradeapp.entities.models.Bids;
import com.example.tradeapp.entities.models.Items;
import com.example.tradeapp.entities.session.UserSession;
import com.example.tradeapp.services.handlers.texthandlers.TextHandler;
import com.example.tradeapp.services.session.SessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.HashMap;
import java.util.Map;

@Component("bidHandler")
@RequiredArgsConstructor
public class SearchArticleBidHandler implements TextHandler {
    private final TextMessageSender textMessageSender;

    private final MessageDirector messageDirector;

    private final SessionService sessionService;

    private final ChatIdFromUpdateComponent updateComponent;

    private final BidClient bidClient;

    private final BidFormer bidFormer;

    private final UserComponent userComponent;

    @Override
    public void handle(UserSession session, Update update) {
        //here is bid insertion
        String text = "";
        String message = update.getMessage().getText();
        String username = userComponent.getUsernameFromMessage(update);
        Long chatId = updateComponent.getChatIdFromUpdate(update);
        Map<String, String> data = session.getUserData();
        if (message.equals("\uD83D\uDC4D\uD83C\uDFFB")) {
            Bids bid = bidFormer.formBid(username, data);
            bidClient.createBid(bid);
            session.setUserData(new HashMap<>(Map.of("", "")));
            text += "Вашу ставку успішно зроблено!";
        } else if (message.equals("\uD83D\uDC4E\uD83C\uDFFB")) {
            session.setUserData(new HashMap<>(Map.of("", "")));
            session.setHandler("market");
            text += "Назад до маркетплейсу...";
        } else {
            session.setHandler("bidHandler");
            text += "Немає такого варіанту відповіді!";
        }
        sessionService.updateSession(chatId, session);
        textMessageSender.sendMessage(messageDirector
                .buildTextMessage(chatId, text));
    }
}
