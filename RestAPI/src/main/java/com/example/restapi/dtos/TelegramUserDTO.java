package com.example.restapi.dtos;

import com.example.restapi.entites.Bid;
import com.example.restapi.entites.Item;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TelegramUserDTO {
    @JsonProperty("username")
    private String username;

    @JsonProperty("tgName")
    private String tgName;

    @JsonProperty("tgSurname")
    private String tgSurname;

    @JsonProperty("chatId")
    private Long chatId;

//    @JsonProperty("items")
//    private List<ItemDTO> items;
//
//    @JsonProperty("bids")
//    private List<BidDTO> bids;
}
