package com.example.tradeapp.services.handlers.queryhandler.articles;

import com.example.tradeapp.builder.director.MessageDirector;
import com.example.tradeapp.client.BidClient;
import com.example.tradeapp.client.ItemClient;
import com.example.tradeapp.client.UserClient;
import com.example.tradeapp.components.BidListFormer;
import com.example.tradeapp.components.ChatIdFromUpdateComponent;
import com.example.tradeapp.components.UserComponent;
import com.example.tradeapp.components.impl.TextMessageSender;
import com.example.tradeapp.entities.models.Bids;
import com.example.tradeapp.entities.models.Users;
import com.example.tradeapp.entities.session.UserSession;
import com.example.tradeapp.services.handlers.queryhandler.QueryHandler;
import com.example.tradeapp.services.session.SessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;
import java.util.Map;

@Component("myBids")
@RequiredArgsConstructor
public class ShowBidsQueryHandler implements QueryHandler {
    private final TextMessageSender textMessageSender;

    private final MessageDirector messageDirector;

    private final SessionService sessionService;

    private final ChatIdFromUpdateComponent updateComponent;

    private final UserComponent userComponent;

    private final BidListFormer bidListFormer;

    private final UserClient userClient;

    @Override
    public void handle(UserSession session, Update update) {
        String message = update.getCallbackQuery().getData();
        Long chatId = updateComponent.getChatIdFromUpdate(update);
        Long itemId = Long.parseLong(message);
        Map<String, String> data = session.getUserData();
        data.put("itemId", itemId.toString());
        Users user = userClient.getUserByUsername(userComponent.getUsernameFromQuery(update));
        String returnMsg = bidListFormer.fromResponseBidList(itemId, user.getId());
        List<String> rows = List.of("Скасувати ставки для обраного товару", "Повернутись до маркетплейсу");
        session.setHandler("bidMenu");
        sessionService.updateSession(chatId, session);
        textMessageSender.sendMessage(messageDirector
                .buildTextMessageWithReplyKeyboard(chatId, "Ось ваші ставки за обраним товаром:\n" + returnMsg, rows));
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
