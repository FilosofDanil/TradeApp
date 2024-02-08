package com.example.tradeapp.services.handlers.texthandlers.impl.bids;

import com.example.tradeapp.builder.director.MessageDirector;
import com.example.tradeapp.client.BidClient;
import com.example.tradeapp.components.BidListFormer;
import com.example.tradeapp.components.ChatIdFromUpdateComponent;
import com.example.tradeapp.components.impl.TextMessageSender;
import com.example.tradeapp.entities.models.Bids;
import com.example.tradeapp.entities.session.UserSession;
import com.example.tradeapp.services.handlers.texthandlers.TextHandler;
import com.example.tradeapp.services.session.SessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Map;

@RequiredArgsConstructor
@Component("showBids")
public class ItemBidMenuHandler implements TextHandler {
    private final TextMessageSender textMessageSender;

    private final MessageDirector messageDirector;

    private final SessionService sessionService;

    private final ChatIdFromUpdateComponent updateComponent;

    private final BidListFormer bidListFormerComponent;

    @Override
    public void handle(UserSession session, Update update) {
        String text = "";
        String message = update.getMessage().getText();
        Long chatId = updateComponent.getChatIdFromUpdate(update);
        Map<String, String> data = session.getUserData();

        if (message.equals("Обрати ставку з найбільшою ціною")) {
            Long itemId = Long.parseLong(data.get("itemId"));
            text += "Ось обрана ставка з найбільшою запропонованою ціною!\n";
            text += bidListFormerComponent.formTopPriceBidString(itemId);
            session.setHandler("market");
        } else if (message.equals("Повернутись до маркетплейсу")) {
            session.setHandler("market");
            text += "Назад до маркетплейсу...";
        } else {
            text += "Немає такого варіанту відповіді!";
            session.setHandler("showBids");
        }
        sessionService.updateSession(chatId, session);
        textMessageSender.sendMessage(messageDirector
                .buildTextMessage(chatId, text));
    }
}
