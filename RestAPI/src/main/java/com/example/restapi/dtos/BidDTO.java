package com.example.restapi.dtos;

import com.example.restapi.entites.Item;
import com.example.restapi.entites.TelegramUser;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BidDTO {
    @JsonProperty("bidPrice")
    private Integer bidPrice;

    @JsonProperty("comment")
    private String comment;

    @JsonProperty("userId")
    private Long userId;

    @JsonProperty("itemId")
    private Long itemId;
}
