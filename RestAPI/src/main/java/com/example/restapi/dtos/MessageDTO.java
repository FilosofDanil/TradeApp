package com.example.restapi.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MessageDTO {
    private Long chatId;
    private String message;
    private String handler;
    private Long itemId;
}
