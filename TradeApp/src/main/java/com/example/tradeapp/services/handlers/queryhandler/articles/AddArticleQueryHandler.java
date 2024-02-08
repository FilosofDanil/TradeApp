package com.example.tradeapp.services.handlers.queryhandler.articles;

import com.example.tradeapp.builder.director.MessageDirector;
import com.example.tradeapp.components.ChatIdFromUpdateComponent;
import com.example.tradeapp.components.MessageSender;
import com.example.tradeapp.entities.constant.Categories;
import com.example.tradeapp.entities.messages.impl.TextMessage;
import com.example.tradeapp.entities.session.UserSession;
import com.example.tradeapp.services.handlers.queryhandler.QueryHandler;
import com.example.tradeapp.services.session.SessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@RequiredArgsConstructor
public class AddArticleQueryHandler implements QueryHandler {
    private final static String query = "➕ Створити оголошення";

    private final MessageSender<TextMessage> textMessageMessageSender;

    private final MessageDirector messageDirector;

    private final SessionService sessionService;

    private final ChatIdFromUpdateComponent updateComponent;

    @Override
    public void handle(UserSession session, Update update) {
        session.setHandler("addArticleCategory");
        String text = "Виберіть категорію вашого товару!";
        Long chatId = updateComponent.getChatIdFromUpdate(update);
        sessionService.updateSession(chatId, session);
        textMessageMessageSender.sendMessage(messageDirector
                .buildTextMessageWithReplyKeyboard(chatId, text, Categories.getCategories()));
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
