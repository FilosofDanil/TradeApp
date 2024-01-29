package com.example.tradeapp.services.handlers.queryhandler.marketplace;

import com.example.tradeapp.builder.director.MessageDirector;
import com.example.tradeapp.components.MessageSender;
import com.example.tradeapp.entities.messages.impl.TextMessage;
import com.example.tradeapp.entities.session.UserSession;
import com.example.tradeapp.services.handlers.queryhandler.QueryHandler;
import com.example.tradeapp.services.session.SessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@RequiredArgsConstructor
public class BackButtonHandler implements QueryHandler {
    private final static String query = "\uD83D\uDD19 Повернутись назад";

    private final MessageSender<TextMessage> textMessageMessageSender;

    private final MessageDirector messageDirector;

    private final SessionService sessionService;

    @Override
    public void handle(UserSession session, Update update) {
        if (session.getHandler().equals("market")){
            String text = "Повернення назад";
            session.setHandler("started");
            sessionService.updateSession(update.getCallbackQuery().getMessage().getChatId(), session);
            textMessageMessageSender.sendMessage(messageDirector
                    .buildTextMessage(update.getMessage().getChatId(), text));
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
