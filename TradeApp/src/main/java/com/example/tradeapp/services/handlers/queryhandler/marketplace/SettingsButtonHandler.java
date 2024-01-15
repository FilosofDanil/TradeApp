package com.example.tradeapp.services.handlers.queryhandler.marketplace;

import com.example.tradeapp.builder.director.MessageDirector;
import com.example.tradeapp.components.MessageSender;
import com.example.tradeapp.entities.constant.Categories;
import com.example.tradeapp.entities.messages.impl.TextMessage;
import com.example.tradeapp.entities.session.UserSession;
import com.example.tradeapp.services.handlers.queryhandler.QueryHandler;
import com.example.tradeapp.services.session.SessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

@Component
@RequiredArgsConstructor
public class SettingsButtonHandler implements QueryHandler {
    private final static String query = "⚙ Змінити/Встановити налаштування";

    private final MessageSender<TextMessage> textMessageSender;

    private final MessageDirector messageDirector;

    private final SessionService sessionService;

    @Override
    public void handle(UserSession session, Update update) {
        if (session.getHandler().equals("market")) {
            session.setHandler("settingsCategories");
            String text = "Спершу оберіть категорії товарів нижче (для пошуку товарів на маркетплейсі):";
            List<String> rows = Categories.getCategories();
            sessionService.updateSession(update.getCallbackQuery().getMessage().getChatId(), session);
            textMessageSender.sendMessage(messageDirector
                    .buildTextMessageWithReplyKeyboard(update.getCallbackQuery().getMessage().getChatId(), text, rows));
        } else {
            String text = "Не актуально. Натисніть /marketplace";
            textMessageSender.sendMessage(messageDirector
                    .buildTextMessage(update.getCallbackQuery().getMessage().getChatId(), text));

        }
    }

    @Override
    public String getCallbackQuery() {
        return query;
    }
}
