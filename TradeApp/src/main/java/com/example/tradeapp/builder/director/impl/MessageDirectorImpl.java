package com.example.tradeapp.builder.director.impl;

import com.example.tradeapp.builder.director.MessageDirector;
import com.example.tradeapp.entities.messages.impl.TextMessage;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

@Component
public class MessageDirectorImpl implements MessageDirector {
    @Override
    public TextMessage buildTextMessage(Long chatId, String text) {
        return new TextMessage(chatId, text);
    }

    @Override
    public TextMessage buildTextMessageWithReplyKeyboard(Long chatId, String text, List<String> rows) {
        List<KeyboardRow> keyboardRows = new ArrayList<>();
        rows.forEach(row -> {
            KeyboardRow keyboardRow = new KeyboardRow();
            keyboardRow.add(row);
            keyboardRows.add(keyboardRow);
        });
        ReplyKeyboardMarkup replyKeyboardMarkup = ReplyKeyboardMarkup.builder()
                .keyboard(keyboardRows)
                .selective(true)
                .resizeKeyboard(true)
                .oneTimeKeyboard(true)
                .build();
        return new TextMessage(chatId, text, replyKeyboardMarkup);
    }

    @Override
    public TextMessage buildTextMessageWithInlineKeyboard(Long chatId, String text, List<String> rows) {
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        rows.forEach(row ->{
            List<InlineKeyboardButton> rowInline = new ArrayList<>();
            InlineKeyboardButton button = new InlineKeyboardButton();
            button.setCallbackData(row);
            button.setText(row);
            rowInline.add(button);
            rowsInline.add(rowInline);
        });
        InlineKeyboardMarkup inlineKeyboardMarkup = InlineKeyboardMarkup.builder()
                .keyboard(rowsInline)
                .build();

        return new TextMessage(chatId, text, inlineKeyboardMarkup);
    }
}
