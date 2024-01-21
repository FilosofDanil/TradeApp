package com.example.tradeapp.components;

import com.example.tradeapp.entities.models.Bids;

import java.util.Map;

public interface BidFormer {
    Bids formBid(String username, Map<String, String> data);
}
