package com.example.tradeapp.entities.constant;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

public final class Categories {
    private Categories() {
    }

    private final static List<String> categories = List.of("Авто", "Нерухомість",
            "Музичні інструменти", "Побут", "Одяг", "Меблі", "Техніка", "Тварини", "Інше");

    public static List<String> getCategories() {
        return categories;
    }
}
