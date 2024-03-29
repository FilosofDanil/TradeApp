package com.example.tradeapp.services.handlers.queryhandler.marketplace;

import com.example.tradeapp.builder.director.MessageDirector;
import com.example.tradeapp.client.BidClient;
import com.example.tradeapp.client.UserClient;
import com.example.tradeapp.components.BidListFormer;
import com.example.tradeapp.components.MessageSender;
import com.example.tradeapp.components.UserComponent;
import com.example.tradeapp.entities.messages.impl.TextMessage;
import com.example.tradeapp.entities.models.Bids;
import com.example.tradeapp.entities.models.Items;
import com.example.tradeapp.entities.models.Users;
import com.example.tradeapp.entities.session.UserSession;
import com.example.tradeapp.services.handlers.queryhandler.QueryHandler;
import com.example.tradeapp.services.session.SessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class MyBidsButtonHandler implements QueryHandler {
    private final static String query = "\uD83D\uDCC8 Мої ставки";

    private final MessageSender<TextMessage> textMessageMessageSender;

    private final MessageDirector messageDirector;

    private final SessionService sessionService;

    private final UserComponent userComponent;

    private final UserClient userClient;

    private final BidClient bidClient;

    private final BidListFormer bidListFormerComponent;

    @Override
    public void handle(UserSession session, Update update) {
        if (session.getHandler().equals("market")) {
            String text = "";
            String username = userComponent.getUsernameFromQuery(update);
            Users user = userClient.getUserByUsername(username);
            List<Bids> bids = bidClient.getAllBidsByUser(user.getId());
            Map<String, String> rows = new HashMap<>();
            if (bids.isEmpty()) {
                text += "Ви поки-що не маєте ставок.";
                textMessageMessageSender.sendMessage(messageDirector
                        .buildTextMessage(update.getMessage().getChatId(), text));
                return;
            } else {
                //TODO Print all bids, depends on user
                text += "Ось ваші ставки(Вибирайте ставки в залежності елементів):";
                List<Items> items = bidListFormerComponent.getAllRelatedItems(username);
                for (Items item : items) {
                    rows.put(item.getItemName(), item.getId().toString());
                }
            }
            session.setHandler("myBids");
            sessionService.updateSession(update.getCallbackQuery().getMessage().getChatId(), session);
            textMessageMessageSender.sendMessage(messageDirector
                    .buildTextMessageWithInlineKeyboard(update.getCallbackQuery().getMessage().getChatId(), text, rows));
        } else {
            String text = "Не актуально. Натисніть /marketplace";
            textMessageMessageSender.sendMessage(messageDirector
                    .buildTextMessage(update.getMessage().getChatId(), text));
        }
    }

    @Override
    public String getCallbackQuery() {
        return query;
    }

    @Override
    public boolean isNumeric() {
        return false;
    }
}
