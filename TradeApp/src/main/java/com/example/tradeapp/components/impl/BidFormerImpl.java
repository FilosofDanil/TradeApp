package com.example.tradeapp.components.impl;

import com.example.tradeapp.client.UserClient;
import com.example.tradeapp.components.BidFormer;
import com.example.tradeapp.entities.models.Bids;
import com.example.tradeapp.entities.models.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class BidFormerImpl implements BidFormer {
    private final UserClient userClient;

    @Override
    public Bids formBid(String username, Map<String, String> data) {
        Users user = userClient.getUserByUsername(username);
        String comment = data.get("bidComment");
        int bidPrice = Integer.parseInt(data.get("bidPrice"));
        int itemId = Integer.parseInt(data.get("currentId"));
        List<Long> itemIds = Arrays.stream(data.get("itemList")
                        .split(" "))
                .map(Long::parseLong)
                .toList();
        return Bids.builder()
                .userId(user.getId())
                .comment(comment)
                .bidPrice(bidPrice)
                .itemId(itemIds.get(itemId))
                .build();
    }
}
