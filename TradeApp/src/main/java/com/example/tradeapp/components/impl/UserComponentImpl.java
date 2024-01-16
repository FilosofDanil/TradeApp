package com.example.tradeapp.components.impl;

import com.example.tradeapp.components.UserComponent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@RequiredArgsConstructor
public class UserComponentImpl implements UserComponent {

    @Override
    public String getUsernameFromMessage(Update update) {
        String username = update.getMessage().getChat().getUserName();
        if (username.isBlank()) {
            username = update.getMessage().getChat().getFirstName();
        }
        return username;
    }

    @Override
    public String getUsernameFromQuery(Update update) {
        String username = update.getCallbackQuery().getMessage().getChat().getUserName();
        if (username.isBlank()) {
            username = update.getCallbackQuery().getMessage().getChat().getFirstName();
        }
        return username;
    }
}
