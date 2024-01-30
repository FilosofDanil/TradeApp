package com.example.tradeapp.services.consumer.impl;

import com.example.tradeapp.builder.director.MessageDirector;
import com.example.tradeapp.components.impl.TextMessageSender;
import com.example.tradeapp.entities.messages.MessageDTO;
import com.example.tradeapp.entities.session.UserSession;
import com.example.tradeapp.services.consumer.Consumer;
import com.example.tradeapp.services.session.SessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ConsumerImpl implements Consumer {
    private final SessionService sessionService;

    private final TextMessageSender textMessageSender;

    private final MessageDirector messageDirector;

    @Override
    @RabbitListener(queues = "NOTIFICATIONS")
    public void consume(MessageDTO messageDTO) {
        Long chatId = messageDTO.getChatId();
        UserSession session = sessionService.getSession(chatId);
        Map<String, String> data = session.getUserData();
        data.put("itemId", messageDTO.getItemId());
        session.setHandler(messageDTO.getHandler());
        List<String> rows = List.of("\uD83D\uDC4D\uD83C\uDFFB");
        sessionService.updateSession(chatId, session);
        textMessageSender.sendMessage(messageDirector
                .buildTextMessageWithReplyKeyboard(chatId, messageDTO.getMessage(), rows));
    }
}
