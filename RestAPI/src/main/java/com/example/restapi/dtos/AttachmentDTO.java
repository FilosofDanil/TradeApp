package com.example.restapi.dtos;

import com.example.restapi.entites.Item;
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
public class AttachmentDTO {
    @JsonProperty("itemType")
    private String itemType;

    @JsonProperty("itemData")
    private String itemData;

    @JsonProperty("itemId")
    private Long itemId;
}
