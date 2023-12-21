package com.example.tradeapp.components;

import com.example.tradeapp.entities.messages.Message;
import com.example.tradeapp.entities.messages.impl.TextMessage;

public interface MessageSender <T extends Message> {
    void sendMessage(T message);
}
