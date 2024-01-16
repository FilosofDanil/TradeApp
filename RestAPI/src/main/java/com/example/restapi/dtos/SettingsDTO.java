package com.example.restapi.dtos;

import com.example.restapi.entites.ItemType;
import com.fasterxml.jackson.annotation.JsonProperty;
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
public class SettingsDTO {
    @JsonProperty("city")
    private String city;

    @JsonProperty("userId")
    private Long userId;

    @JsonProperty("categories")
    private List<String> categories;
}
