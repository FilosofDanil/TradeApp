package com.example.tradeapp.components.impl;

import com.example.tradeapp.client.BidClient;
import com.example.tradeapp.client.ItemClient;
import com.example.tradeapp.client.UserClient;
import com.example.tradeapp.components.BidListFormer;
import com.example.tradeapp.entities.models.Bids;
import com.example.tradeapp.entities.models.Items;
import com.example.tradeapp.entities.models.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class BidListFormerImpl implements BidListFormer {
    private final BidClient bidClient;

    private final UserClient userClient;

    private final ItemClient itemClient;

    @Override
    public List<String> fromResponseBidList(Long itemId) {
        List<Bids> bids = bidClient.getAllBidsByItem(itemId);
        List<String> resultList = new ArrayList<>();
        for (Bids bid : bids) {
            StringBuilder stringBuilder = new StringBuilder();
            Users user = userClient.getUserById(bid.getUserId());
            stringBuilder.append(user.getTgName()).append(": ");
            stringBuilder.append("\n");
            stringBuilder.append("Запропонована ціна: ").append(bid.getBidPrice());
            stringBuilder.append("@").append(user.getUsername());
            stringBuilder.append("Коментар: ").append(bid.getComment());
            resultList.add(stringBuilder.toString());
        }
        return resultList;
    }

    @Override
    public List<Items> getAllRelatedItems(String username) {
        List<Items> items = itemClient.getAllItemsByUserHavingBids(username);
        return items;
    }
}
