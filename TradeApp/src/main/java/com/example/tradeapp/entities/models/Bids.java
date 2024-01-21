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
public class Bids {
    private Integer bidPrice;
    private String comment;
    private Long userId;
    private Long itemId;
}
