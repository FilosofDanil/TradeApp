package com.example.tradeapp.components.impl;

import com.example.tradeapp.client.ItemClient;
import com.example.tradeapp.client.ItemTypeClient;
import com.example.tradeapp.client.UserClient;
import com.example.tradeapp.components.ChatIdFromUpdateComponent;
import com.example.tradeapp.components.SendArticles;
import com.example.tradeapp.components.SettingsComponent;
import com.example.tradeapp.components.UserComponent;
import com.example.tradeapp.entities.models.ItemType;
import com.example.tradeapp.entities.models.Items;
import com.example.tradeapp.entities.models.Settings;
import com.example.tradeapp.entities.models.Users;
import com.example.tradeapp.entities.session.UserSession;
import com.example.tradeapp.services.session.SessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class SendArticlesImpl implements SendArticles {
    private final SettingsComponent settingsComponent;

    private final UserComponent userComponent;

    private final UserClient userClient;

    private final ItemTypeClient itemTypeClient;

    private final ItemClient itemClient;

    private final ChatIdFromUpdateComponent updateComponent;

    private final SessionService sessionService;

    @Override
    public void sendArticlesToUser(Update update, UserSession session) {
        String username = userComponent.getUsernameFromMessage(update);
        Long chatId = updateComponent.getChatIdFromUpdate(update);
        Settings settings = settingsComponent.getSettingsByUsername(username);
        List<String> settingsCategories = settings.getCategories();
        List<ItemType> categories = itemTypeClient.getAllItemTypes()
                .stream()
                .filter(itemType -> settingsCategories.contains(itemType
                        .getName()))
                .toList();
        List<Items> items = itemClient.getAllItems()
                .stream()
                .filter(item -> !categories
                        .stream()
                        .filter(itemType -> itemType.getId()
                                .equals(item.getItemTypeId()))
                        .toList()
                        .isEmpty() && isSameCityWithItem(settings, item))
                .toList();
        //here is data insertion
        session.setUserData(insertItems(session, items));
        sessionService.updateSession(chatId, session);
    }

    private Map<String, String> insertItems(UserSession session, List<Items> items){
        Map<String, String> data = session.getUserData();
        StringBuilder stringBuilder = new StringBuilder();
        items.forEach(item -> {
            stringBuilder.append(item.getId());
            stringBuilder.append(" ");
        });
        data.put("itemList", stringBuilder.toString());
        return data;
    }

    private boolean isSameCityWithItem(Settings settings, Items item) {
        if (settings.getCity().equals("Вся Україна")) {
            return true;
        } else {
            Users user = userClient.getUserById(item.getUserId());
            Settings itemSettings = settingsComponent
                    .getSettingsByUsername(user.getUsername());
            return itemSettings.getCity().equals(settings.getCity())
                    || itemSettings.getCity().equals("Вся Україна");
        }
    }
}


