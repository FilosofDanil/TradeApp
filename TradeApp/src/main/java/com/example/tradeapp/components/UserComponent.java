package com.example.tradeapp.components;

import com.example.tradeapp.entities.models.Users;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface UserComponent {
    String getUsernameFromMessage(Update update);

    String getUsernameFromQuery(Update update);
}
