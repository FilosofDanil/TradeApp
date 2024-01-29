package com.example.tradeapp.services.handlers.texthandlers.impl.search;

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

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Component("bidPrice")
@RequiredArgsConstructor
public class SearchArticleBidPriceHandler implements TextHandler {
    private final TextMessageSender textMessageSender;

    private final MessageDirector messageDirector;

    private final SessionService sessionService;

    private final ChatIdFromUpdateComponent updateComponent;

    private final ItemClient itemClient;

    @Override
    public void handle(UserSession session, Update update) {
        String text = "";
        String message = update.getMessage().getText();
        Long chatId = updateComponent.getChatIdFromUpdate(update);
        Map<String, String> data = session.getUserData();
        int itemId = Integer.parseInt(data.get("currentId"));
        List<Long> itemIds = Arrays.stream(data.get("itemList")
                        .split(" "))
                .map(Long::parseLong)
                .toList();
        Items item = itemClient.getItemById(itemIds.get(itemId));
        int itemPrice = item.getStartPrice();
        int bidPrice = item.getBidPrice();
        try {
            Integer price = checkPrice(message);

            if (price <= itemPrice || price <= bidPrice) {
                throw new IllegalArgumentException();
            }
            data.put("bidPrice", price.toString());
            session.setHandler("bidComment");
            session.setUserData(data);
            text += "Надішліть невеликий коментар(не більше речення)";
        } catch (NumberFormatException e) {
            session.setHandler("bidPrice");
            text += "Неправильно введена ціна. Вкажіть ціну товару в цілому числі(в гривнях)";
        } catch (IllegalArgumentException e) {
            session.setHandler("bidPrice");
            text += "Введіть будь-ласка ціну ставки, яке перевищує останню(зараз це " + itemPrice + ")";
        }
        sessionService.updateSession(chatId, session);
        textMessageSender.sendMessage(messageDirector
                .buildTextMessage(chatId, text));
    }

    private Integer checkPrice(String message) throws NumberFormatException {
        return Integer.parseInt(message);
    }
}
