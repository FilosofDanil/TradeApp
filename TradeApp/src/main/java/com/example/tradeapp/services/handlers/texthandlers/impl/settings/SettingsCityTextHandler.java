package com.example.tradeapp.services.handlers.texthandlers.impl.settings;

import com.example.tradeapp.builder.director.MessageDirector;
import com.example.tradeapp.components.impl.TextMessageSender;
import com.example.tradeapp.entities.constant.Cities;
import com.example.tradeapp.entities.session.UserSession;
import com.example.tradeapp.services.handlers.texthandlers.TextHandler;
import com.example.tradeapp.services.session.SessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;
import java.util.Map;

@Component("settingsCity")
@RequiredArgsConstructor
public class SettingsCityTextHandler implements TextHandler {
    private final TextMessageSender textMessageSender;

    private final MessageDirector messageDirector;

    private final SessionService sessionService;

    @Override
    public void handle(UserSession session, Update update) {
        String text = "";
        String msg = update.getMessage().getText();
        List<String> cities = Cities.getCities();
        if (cities.contains(msg)) {
            Map<String, String> data = session.getUserData();
            data.put("city", msg);
            text+="Ви обрали: " + msg;
            text+="\n Ви все ввели правильно?!";
            List<String> rows = List.of("Так", "Ні");
            session.setHandler("settings");
            sessionService.updateSession(update.getMessage().getChatId(), session);
            textMessageSender.sendMessage(messageDirector
                    .buildTextMessageWithReplyKeyboard(update.getMessage().getChatId(), text, rows));
        } else {
            text += "Нема такого варіанту відповіді!";
            textMessageSender.sendMessage(messageDirector
                    .buildTextMessage(update.getMessage().getChatId(), text));
        }
    }
}
