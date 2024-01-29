package com.example.tradeapp.services.handlers.queryhandler.articles;

import com.example.tradeapp.entities.session.UserSession;
import com.example.tradeapp.services.handlers.queryhandler.QueryHandler;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class ShowArticleQueryHandler implements QueryHandler {
    @Override
    public void handle(UserSession session, Update update) {

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
