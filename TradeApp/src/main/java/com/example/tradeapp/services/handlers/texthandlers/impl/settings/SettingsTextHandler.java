package com.example.tradeapp.services.handlers.texthandlers.impl.settings;

import com.example.tradeapp.builder.director.MessageDirector;
import com.example.tradeapp.components.SettingsComponent;
import com.example.tradeapp.components.impl.TextMessageSender;
import com.example.tradeapp.entities.constant.Categories;
import com.example.tradeapp.entities.session.UserSession;
import com.example.tradeapp.services.handlers.texthandlers.TextHandler;
import com.example.tradeapp.services.session.SessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component("settings")
@RequiredArgsConstructor
public class SettingsTextHandler implements TextHandler {
    private final TextMessageSender textMessageSender;

    private final MessageDirector messageDirector;

    private final SessionService sessionService;

    private final SettingsComponent settingsComponent;

    @Override
    public void handle(UserSession session, Update update) {
        String text = "";
        String msg = update.getMessage().getText();
        if (msg.equals("Так")) {
            //inserting data into database
            String username = update.getMessage().getChat().getUserName();
            if (username.isBlank()) {
                username = update.getMessage().getChat().getFirstName();
            }

            settingsComponent.saveSettings(username, session.getUserData());
            text += "Ваші налаштування збережено!";
            session.setUserData(new HashMap<>(Map.of("", "")));
            sessionService.updateSession(update.getMessage().getChatId(), session);
            textMessageSender.sendMessage(messageDirector
                    .buildTextMessage(update.getMessage().getChatId(), text));
        } else if (msg.equals("Ні")) {
            //restart settings
            session.setUserData(new HashMap<>(Map.of("", "")));
            session.setHandler("settingsCategories");
            text += "Добре, тоді налаштуємо все заново!";
            text += "\n Оберіть категорії товарів нижче (для пошуку товарів на маркетплейсі):";
            List<String> rows = Categories.getCategories();
            sessionService.updateSession(update.getCallbackQuery().getMessage().getChatId(), session);
            textMessageSender.sendMessage(messageDirector
                    .buildTextMessageWithReplyKeyboard(update.getCallbackQuery().getMessage().getChatId(), text, rows));
        } else {
            text += "Нема такого варіанту відповіді!";
            textMessageSender.sendMessage(messageDirector
                    .buildTextMessage(update.getMessage().getChatId(), text));
        }
    }
}
