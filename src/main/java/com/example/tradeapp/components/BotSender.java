package com.example.tradeapp.components;

import com.example.tradeapp.configs.TelegramBotConfiguration;
import com.example.tradeapp.entities.messages.impl.TextMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Component
@Slf4j
public class BotSender extends DefaultAbsSender {
    private final TelegramBotConfiguration botConfiguration;

    protected BotSender(TelegramBotConfiguration botConfiguration) {
        super(new DefaultBotOptions());
        this.botConfiguration = botConfiguration;
    }

    @Override
    public String getBotToken() {
        return botConfiguration.getToken();
    }
}
