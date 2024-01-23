package com.example.tradeapp.services.handlers.commandhandlers.impl;

import com.example.tradeapp.builder.director.MessageDirector;
import com.example.tradeapp.components.ChatIdFromUpdateComponent;
import com.example.tradeapp.components.MessageSender;
import com.example.tradeapp.entities.messages.impl.TextMessage;
import com.example.tradeapp.entities.session.UserSession;
import com.example.tradeapp.services.handlers.commandhandlers.CommandHandler;
import com.example.tradeapp.services.session.SessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@RequiredArgsConstructor
public class HelpCommandHandler implements CommandHandler {
    private final static String command = "/help";

    private final MessageSender<TextMessage> textMessageMessageSender;

    private final MessageDirector messageDirector;

    private final SessionService sessionService;

    private final ChatIdFromUpdateComponent updateComponent;

    @Override
    public void handle(UserSession session, Update update) {
        Long chatId = updateComponent.getChatIdFromUpdate(update);
        String text = "Вітаємо вас в розділі допомоги і запитань! Якщо вам " +
                "необхідна будь-яка інформація з приводу користування нашим " +
                "телеграм ботом, будь-ласка оберіть відповідний розділ нижче, " +
                "якщо ж у вас виникли особливі запитання, будь-ласка зверніться " +
                "до нас по допомогу в розділ /support описавши вашу проблему, дякуємо!";
        //help option will be added soon
        textMessageMessageSender.sendMessage(messageDirector
                .buildTextMessage(chatId, text));
        session.setHandler("help");
        sessionService.updateSession(chatId, session);
    }

    @Override
    public String getCommand() {
        return command;
    }
}
