package com.example.tradeapp.services.producer;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface Producer {
    void produce(String rabbitQueue, Update update);
}
