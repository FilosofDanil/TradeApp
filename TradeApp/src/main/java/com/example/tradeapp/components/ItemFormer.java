package com.example.tradeapp.components;

import com.example.tradeapp.entities.models.Items;

import java.util.Map;

public interface ItemFormer {
    Items formItem(Map<String, String> data, String username);
}
