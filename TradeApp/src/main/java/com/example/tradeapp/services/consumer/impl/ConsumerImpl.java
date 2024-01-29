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
        UserSession session = sessionService.getSession(messageDTO.getChatId());
        Map<String, String> data = session.getUserData();
        data.put("itemId", messageDTO.getItemId());
        session.setHandler(messageDTO.getHandler());
        textMessageSender.sendMessage(messageDirector
                .buildTextMessage(messageDTO.getChatId(), messageDTO.getMessage()));
    }
}
