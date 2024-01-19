package com.example.tradeapp.components.impl;

import com.example.tradeapp.builder.director.MessageDirector;
import com.example.tradeapp.components.ChatIdFromUpdateComponent;
import com.example.tradeapp.components.MessageSender;
import com.example.tradeapp.components.StartSearchingComponent;
import com.example.tradeapp.entities.messages.impl.TextMessage;
import com.example.tradeapp.entities.session.UserSession;
import com.example.tradeapp.services.session.SessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class StartSearchingComponentImpl implements StartSearchingComponent {
    private final MessageSender<TextMessage> textMessageMessageSender;

    private final MessageDirector messageDirector;

    private final SessionService sessionService;

    private final ChatIdFromUpdateComponent updateComponent;

    @Override
    public void startSearchingProcess(Update update, UserSession session) {
        String text = "Починаємо пошук товарів, відповідно ваших налаштувань... Щоб почати натисніть \uD83D\uDE80";
        Long chatId = updateComponent.getChatIdFromUpdate(update);
        Map<String, String> data = session.getUserData();
        data.put("startedSearch", "false");
        session.setUserData(data);
        List<String> rows = List.of("\uD83D\uDE80");
        textMessageMessageSender.sendMessage(messageDirector
                .buildTextMessageWithReplyKeyboard(chatId, text, rows));
        session.setHandler("searching");

        sessionService.updateSession(chatId, session);
    }
}
