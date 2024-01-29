package com.example.tradeapp.services.consumer.impl;

import com.example.tradeapp.builder.director.MessageDirector;
import com.example.tradeapp.components.impl.TextMessageSender;
import com.example.tradeapp.entities.messages.MessageDTO;
import com.example.tradeapp.services.consumer.Consumer;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class ConsumerImpl implements Consumer {
    @Override
    @RabbitListener(queues = "NOTIFICATIONS")
    public void consume(MessageDTO messageDTO) {

    }
}
