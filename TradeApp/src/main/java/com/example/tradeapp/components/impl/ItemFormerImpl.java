package com.example.tradeapp.components.impl;

import com.example.tradeapp.client.ItemTypeClient;
import com.example.tradeapp.client.UserClient;
import com.example.tradeapp.components.ItemFormer;
import com.example.tradeapp.entities.models.ItemType;
import com.example.tradeapp.entities.models.Items;
import com.example.tradeapp.entities.models.Users;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class ItemFormerImpl implements ItemFormer {
    private final ItemTypeClient itemTypeClient;

    private final UserClient userClient;

    @Override
    @SneakyThrows
    public Items formItem(Map<String, String> data, String username) {
        Integer bidPrice = Integer.parseInt(data.get("articlePrice"));
        String name = data.get("articleName");
        String description = data.get("articleDescription");
        String date = data.get("articleDate");
        String category = data.get("articleCategory");
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        Users user = userClient.getUserByUsername(username);
        List<ItemType> itemTypes = itemTypeClient.getAllItemTypes()
                .stream()
                .filter(itemType -> itemType.getName().equals(category))
                .toList();
        return Items.builder()
                .itemName(name)
                .userId(user.getId())
                .itemTypeId(itemTypes.get(0).getId())
                .placementDate(new Date())
                .description(description)
                .expirationDate(formatter.parse(date))
                .startPrice(bidPrice)
                .build();
    }
}
