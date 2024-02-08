package com.example.tradeapp.entities.messages;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MessageDTO {
    @JsonProperty("chatId")
    private Long chatId;
    @JsonProperty("message")
    private String message;
    @JsonProperty("handler")
    private String handler;
    @JsonProperty("itemId")
    private String itemId;
}
