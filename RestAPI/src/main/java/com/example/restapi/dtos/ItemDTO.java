package com.example.restapi.dtos;

import com.example.restapi.entites.Bid;
import com.example.restapi.entites.Item;
import com.example.restapi.entites.ItemType;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ItemDTO {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("itemName")
    private String itemName;

    @JsonProperty("description")
    private String description;

    @JsonProperty("startPrice")
    private Integer startPrice;

    @JsonProperty("bidPrice")
    private Integer bidPrice;

    @JsonProperty("userId")
    private Long userId;

    @JsonProperty("itemTypeId")
    private Long itemTypeId;

    @JsonProperty("placementDate")
    private Date placementDate;

    @JsonProperty("expirationDate")
    private Date expirationDate;

    @JsonProperty("expired")
    private Boolean expired;

//    @JsonProperty("bids")
//    private List<BidDTO> bids;
//
//    @JsonProperty("attachments")
//    private List<AttachmentDTO> attachments;
}
