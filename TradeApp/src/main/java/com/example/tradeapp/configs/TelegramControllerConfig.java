package com.example.tradeapp.configs;

import com.example.tradeapp.controller.TelegramController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Configuration
public class TelegramControllerConfig {
    @Bean
    public TelegramBotsApi telegramBotsApi(TelegramController telegramController) throws TelegramApiException {
        var api = new TelegramBotsApi(DefaultBotSession.class);
        api.registerBot(telegramController);
        return api;
    }
}
