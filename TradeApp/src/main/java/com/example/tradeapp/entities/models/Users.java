package com.example.tradeapp.entities.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Users {
    private Long id;
    private String username;
    private String tgName;
    private String tgSurname;
    private Long chatId;
}
