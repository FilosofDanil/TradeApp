package com.example.restapi.producer.impl;

import com.example.restapi.dtos.MessageDTO;
import com.example.restapi.producer.Producer;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProducerImpl implements Producer {
    private final RabbitTemplate rabbitTemplate;

    @Override
    public void produce(String rabbitQueue, MessageDTO messageDTO) {
        rabbitTemplate.convertAndSend(rabbitQueue, messageDTO);
    }
}
