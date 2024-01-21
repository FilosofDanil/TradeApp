package com.example.tradeapp.entities.constant;

import java.util.List;

public final class Cities {
    private Cities() {
    }

    private final static List<String> CITIES = List.of("Київ", "Житомир", "Рівне",
            "Луцьк", "Львів", "Івано-Франківськ", "Тернопіль", "Ужгород",
            "Хмельницький", "Вінниця", "Кропивницький", "Черкаси", "Одеса",
            "Миколаїв","Херсон", "Дніпро","Запоріжжя","Полтава","Харків",
            "Суми", "Чернігів", "Чернівці", "Вся Україна");

    public static List<String> getCities() {
        return CITIES;
    }
}
