package com.example.tradeapp.services.handlers.texthandlers.impl.bids;

import com.example.tradeapp.builder.director.MessageDirector;
import com.example.tradeapp.client.BidClient;
import com.example.tradeapp.client.UserClient;
import com.example.tradeapp.components.ChatIdFromUpdateComponent;
import com.example.tradeapp.components.UserComponent;
import com.example.tradeapp.components.impl.TextMessageSender;
import com.example.tradeapp.entities.models.Bids;
import com.example.tradeapp.entities.models.Users;
import com.example.tradeapp.entities.session.UserSession;
import com.example.tradeapp.services.handlers.texthandlers.TextHandler;
import com.example.tradeapp.services.session.SessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;
import java.util.Map;

@Component("bidMenu")
@RequiredArgsConstructor
public class BidMenuHandler implements TextHandler {
    private final TextMessageSender textMessageSender;

    private final MessageDirector messageDirector;

    private final SessionService sessionService;

    private final ChatIdFromUpdateComponent updateComponent;

    private final BidClient bidClient;

    private final UserComponent userComponent;

    private final UserClient userClient;

    @Override
    public void handle(UserSession session, Update update) {
        String text = "";
        String message = update.getMessage().getText();
        Long chatId = updateComponent.getChatIdFromUpdate(update);
        Map<String, String> data = session.getUserData();
        if (message.equals("Скасувати ставки для обраного товару")) {
            Long itemId = Long.parseLong(data.get("itemId"));
            String username = userComponent.getUsernameFromMessage(update);
            Users user = userClient.getUserByUsername(username);
            bidClient.getAllBidsByItem(itemId)
                    .stream()
                    .filter(bid -> bid.getUserId().equals(user.getId()))
                    .forEach(bid -> {
                        bidClient.deleteBid(bid.getId());
                    });
            text += "Ваші ставки успішно скасовано!";
            session.setHandler("market");
        } else if (message.equals("Повернутись до маркетплейсу")) {
            session.setHandler("market");
            text += "Назад до маркетплейсу...";
        } else {
            text += "Немає такого варіанту відповіді!";
            session.setHandler("bidMenu");
        }
        sessionService.updateSession(chatId, session);
        textMessageSender.sendMessage(messageDirector
                .buildTextMessage(chatId, text));
    }
}
