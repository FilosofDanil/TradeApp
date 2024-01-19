package com.example.tradeapp.entities.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Items {
    private Long id;
    private String itemName;
    private String description;
    private Integer startPrice;
    private Integer bidPrice;
    private Long userId;
    private Long itemTypeId;
    private Date placementDate;
    private Date expirationDate;
}
