package com.example.tradeapp.components.impl;

import com.example.tradeapp.components.BotSender;
import com.example.tradeapp.components.MessageSender;
import com.example.tradeapp.entities.messages.impl.TextMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Component("textMessageSender")
@Slf4j
@RequiredArgsConstructor
public class TextMessageSender implements MessageSender<TextMessage> {
    private final BotSender botSender;

    @Override
    public void sendMessage(TextMessage message) {
        SendMessage sendMessage = SendMessage
                .builder()
                .text(message.getMessage())
                .chatId(message.getChatId().toString())
                //Other possible parse modes: MARKDOWNV2, MARKDOWN, which allows to make text bold, and all other things
                .parseMode(ParseMode.HTML)
                .build();
        if (message.getReplyKeyboard()!=null){
            sendMessage.setReplyMarkup(message.getReplyKeyboard());
        }
        if (message.getInlineKeyboardMarkup()!=null){
            sendMessage.setReplyMarkup(message.getInlineKeyboardMarkup());
        }
        execute(sendMessage);
    }

    private void execute(BotApiMethod botApiMethod) {
        try {
            botSender.execute(botApiMethod);
        } catch (Exception e) {
            log.error("Exception: ", e);
        }
    }
}
