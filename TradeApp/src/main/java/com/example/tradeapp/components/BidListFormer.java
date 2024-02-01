package com.example.tradeapp.components;

import com.example.tradeapp.entities.models.Items;

import java.util.List;

public interface BidListFormer {
    String fromResponseBidList(Long itemId);

    String fromResponseBidList(Long itemId, Long userId);

    String formTopPriceBidString(Long itemId);

    List<Items> getAllRelatedItems(String username);
}
