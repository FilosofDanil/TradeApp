package com.example.tradeapp.services.handlers.queryhandler.marketplace;

import com.example.tradeapp.builder.director.MessageDirector;
import com.example.tradeapp.components.ChatIdFromUpdateComponent;
import com.example.tradeapp.components.MessageSender;
import com.example.tradeapp.components.StartSearchingComponent;
import com.example.tradeapp.entities.messages.impl.TextMessage;
import com.example.tradeapp.entities.session.UserSession;
import com.example.tradeapp.services.handlers.queryhandler.QueryHandler;
import com.example.tradeapp.services.session.SessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.QueryAnnotation;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class StartSearchingHandler implements QueryHandler {
    private final static String query = "\uD83D\uDC41 Перегляд товарів";

    private final MessageSender<TextMessage> textMessageMessageSender;

    private final MessageDirector messageDirector;

    private final StartSearchingComponent searchingComponent;

    @Override
    public void handle(UserSession session, Update update) {
        if (session.getHandler().equals("market")){
            searchingComponent.startSearchingProcess(update, session);
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
