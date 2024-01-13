package com.example.restapi.dtos;

import com.example.restapi.entites.Bid;
import com.example.restapi.entites.Item;
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

    @JsonProperty("placementDate")
    private Date placementDate;

    @JsonProperty("expirationDate")
    private Date expirationDate;

    @JsonProperty("bids")
    private List<BidDTO> bids;

    @JsonProperty("attachments")
    private List<AttachmentDTO> attachments;
}
