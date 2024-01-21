package com.example.tradeapp.builder.director.impl;

import com.example.tradeapp.builder.director.KeyboardBuilder;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

@Component
public class KeyboardBuilderImpl implements KeyboardBuilder {
    @Override
    public ReplyKeyboardMarkup buildReplyKeyboard(List<String> rows) {
        List<KeyboardRow> keyboardRows = new ArrayList<>();
        rows.forEach(row -> {
            KeyboardRow keyboardRow = new KeyboardRow();
            keyboardRow.add(row);
            keyboardRows.add(keyboardRow);
        });
        return ReplyKeyboardMarkup.builder()
                .keyboard(keyboardRows)
                .selective(true)
                .resizeKeyboard(true)
                .oneTimeKeyboard(true)
                .build();
    }
}
