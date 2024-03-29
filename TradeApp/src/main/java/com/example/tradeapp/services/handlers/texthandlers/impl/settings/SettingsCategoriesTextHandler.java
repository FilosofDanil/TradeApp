package com.example.tradeapp.services.handlers.texthandlers.impl.settings;

import com.example.tradeapp.builder.director.MessageDirector;
import com.example.tradeapp.components.ChatIdFromUpdateComponent;
import com.example.tradeapp.components.impl.TextMessageSender;
import com.example.tradeapp.entities.constant.Categories;
import com.example.tradeapp.entities.constant.Cities;
import com.example.tradeapp.entities.session.UserSession;
import com.example.tradeapp.services.handlers.texthandlers.TextHandler;
import com.example.tradeapp.services.session.SessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component("settingsCategories")
@RequiredArgsConstructor
public class SettingsCategoriesTextHandler implements TextHandler {
    private final TextMessageSender textMessageSender;

    private final MessageDirector messageDirector;

    private final SessionService sessionService;

    private final ChatIdFromUpdateComponent updateComponent;

    @Override
    public void handle(UserSession session, Update update) {
        String text = "";
        String message = update.getMessage().getText();
        Long chatId = updateComponent.getChatIdFromUpdate(update);
        Map<String, String> data = session.getUserData();
        if (message.equals("Це все, завершити.") && !data.isEmpty()) {
            // here is data insertion in DB
            session.setHandler("settingsCity");
            text += "Добре! Ваші обрані категорії: "; // add the list of categories
            for (String category : session.getUserData().values()) {
                text += category;
                text += " ";
            }
            text += "\n Далі виберіть ваше місто(або обл. центр) зі списку:";
            List<String> rows = Cities.getCities();
            session.setHandler("settingsCity");
            sessionService.updateSession(chatId, session);
            textMessageSender.sendMessage(messageDirector
                    .buildTextMessageWithReplyKeyboard(update.getMessage().getChatId(), text, rows));
            return;
        } else if (Categories.getCategories().contains(message)) {
            text = checkData(session, update, text);
            session.setHandler("settingsCategories");
            sessionService.updateSession(chatId, session);
        } else {
            text += "Нема такого варіанту відповіді!";
        }
        List<String> rows = new ArrayList<>(Categories.getCategories());
        rows.add("Це все, завершити.");
        textMessageSender.sendMessage(messageDirector
                .buildTextMessageWithReplyKeyboard(chatId, text, rows));
    }

    private String checkData(UserSession session, Update update, String text) {
        String message = update.getMessage().getText();
        Map<String, String> data = session.getUserData();
        Long chatId = updateComponent.getChatIdFromUpdate(update);
        int size = data.size();
        for (String category : data.values()) {
            if (category.equals(message)) {
                text += "Ви вже обирали цю категорію ";
                return text;
            }
        }
        data.put("category" + size, message);
        sessionService.updateSession(chatId, session);
        text += "Ви обрали варіант: " + message;
        return text;
    }
}
