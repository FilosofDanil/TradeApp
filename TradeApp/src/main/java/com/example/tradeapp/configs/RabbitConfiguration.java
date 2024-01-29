package com.example.tradeapp.configs;

import com.rabbitmq.client.AMQP;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfiguration {

    @Bean
    public MessageConverter jsonConverter(){
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public Queue textMessageQueue(){
        return new Queue("TEXT_MESSAGES_QUEUE");
    }

    @Bean
    public Queue answerMessageQueue(){
        return new Queue("ANSWER_MESSAGES_QUEUE");
    }

    @Bean
    public Queue notificationsQueue(){
        return new Queue("NOTIFICATIONS");
    }
}
