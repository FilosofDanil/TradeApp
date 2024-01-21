package com.example.tradeapp.services.handlers.queryhandler.marketplace;

import com.example.tradeapp.builder.director.MessageDirector;
import com.example.tradeapp.client.ItemClient;
import com.example.tradeapp.components.MessageSender;
import com.example.tradeapp.components.SettingsComponent;
import com.example.tradeapp.components.UserComponent;
import com.example.tradeapp.entities.models.Items;
import com.example.tradeapp.entities.models.Settings;
import com.example.tradeapp.entities.messages.impl.TextMessage;
import com.example.tradeapp.entities.session.UserSession;
import com.example.tradeapp.services.handlers.queryhandler.QueryHandler;
import com.example.tradeapp.services.session.SessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class MyArticlesButtonHandler implements QueryHandler {
    private final static String query = "\uD83D\uDE9B Мої Товари";

    private final MessageSender<TextMessage> textMessageMessageSender;

    private final MessageDirector messageDirector;

    private final SessionService sessionService;

    private final ItemClient itemClient;

    private final SettingsComponent settingsComponent;

    private final UserComponent userComponent;

    @Override
    public void handle(UserSession session, Update update) {
        if (session.getHandler().equals("market")) {
            String text = "";
            String username = userComponent.getUsernameFromQuery(update);
            Settings settings = settingsComponent.getSettingsByUsername(username);
            List<Items> items = itemClient.getAllItemsByUser(username);
            List<String> rows = new ArrayList<>(List.of("➕ Створити оголошення"));
            if (items.isEmpty()) {
                text += "Ви поки-що не маєте виставлених товарів." +
                        "\n Щоб додати товар до продажу натисніть відповідну кнопку";
            } else {
                text += "Ось ваші товари:";
                for (Items item : items) {
                    rows.add(item.getItemName());
                }
            }
            session.setHandler("myItems");
            sessionService.updateSession(update.getCallbackQuery().getMessage().getChatId(), session);
            textMessageMessageSender.sendMessage(messageDirector
                    .buildTextMessageWithInlineKeyboard(update.getCallbackQuery().getMessage().getChatId(), text, rows));
            //error handling then
//            try {
//
//            } catch (IllegalArgumentException e) {
//                String text = "Спершу оберіть налаштування!";
//                textMessageMessageSender.sendMessage(messageDirector
//                        .buildTextMessage(update.getMessage().getChatId(), text));
//            }
        } else {
            String text = "Не актуально. Натисніть /marketplace";
            textMessageMessageSender.sendMessage(messageDirector
                    .buildTextMessage(update.getCallbackQuery().getMessage().getChatId(), text));
        }
    }

    @Override
    public String getCallbackQuery() {
        return query;
    }
}
