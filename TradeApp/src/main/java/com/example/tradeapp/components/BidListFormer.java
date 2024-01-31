package com.example.tradeapp.components;

import com.example.tradeapp.entities.models.Items;

import java.util.List;

public interface BidListFormer {
    List<String> fromResponseBidList(Long itemId);

    List<Items> getAllRelatedItems(String username);
}
