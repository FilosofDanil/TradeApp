package com.example.tradeapp.configs;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@Data
@PropertySource("application.yml")
public class TelegramBotConfiguration {
    @Value("${bot.name}")
    private String name;

    @Value("${bot.token}")
    private String token;


}
