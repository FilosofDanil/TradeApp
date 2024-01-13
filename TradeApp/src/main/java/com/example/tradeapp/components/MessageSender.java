package com.example.tradeapp.components;

import com.example.tradeapp.entities.messages.Message;

public interface MessageSender <T extends Message> {
    void sendMessage(T message);
}
