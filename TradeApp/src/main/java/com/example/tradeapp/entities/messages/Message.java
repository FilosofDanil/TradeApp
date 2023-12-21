package com.example.tradeapp.entities.messages;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class Message {
    private Long chatId;
}
