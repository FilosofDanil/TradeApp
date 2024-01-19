package com.example.tradeapp.builder.director;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

import java.util.List;

public interface KeyboardBuilder {
    ReplyKeyboardMarkup buildReplyKeyboard(List<String> rows);
}
