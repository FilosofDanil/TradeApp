package com.example.tradeapp.services.consumer;

import com.example.tradeapp.entities.messages.MessageDTO;

public interface Consumer {
    void consume(MessageDTO messageDTO);
}
