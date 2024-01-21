package com.example.tradeapp.services.userdata;

import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface UserService {
    void checkUser(Message message);
}
