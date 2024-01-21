package com.example.tradeapp.services.userdata.impl;

import com.example.tradeapp.client.UserClient;
import com.example.tradeapp.entities.models.Users;
import com.example.tradeapp.services.userdata.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserClient userClient;

    @Override
    public void checkUser(Message message) {
        String username = message.getChat().getUserName();
        String tgName = message.getChat().getFirstName();
        String tgSurname = message.getChat().getLastName();
        Long chatId = message.getChatId();
        if (username == null) {
            username = tgName;
        }
        if(tgSurname == null){
            tgSurname = tgName;
        }
        if (!userClient.checkUserByUsername(username) && !userClient.checkUserByTgName(tgName)) {
            Users user = Users.builder()
                    .username(username)
                    .tgName(tgName)
                    .tgSurname(tgSurname)
                    .chatId(chatId)
                    .build();
            userClient.createUser(user);
        }
    }
}
