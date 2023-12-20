package com.example.tradeapp.services.statemanager.impl;

import com.example.tradeapp.entities.session.UserSession;
import com.example.tradeapp.services.handlers.Handler;
import com.example.tradeapp.services.handlers.commandhandlers.CommandHandler;
import com.example.tradeapp.services.statemanager.StateService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StateServiceImpl implements StateService {

    private final List<CommandHandler> commandHandlers;

    private final ApplicationContext context;

    @Override
    public void handle(UserSession session, Update update) {
        if (update.getMessage().isCommand()) {
            String command = update.getMessage().getText();
            for (CommandHandler commandHandler : commandHandlers) {
                if (commandHandler.getCommand().equals(command)) {
                    commandHandler.handle(session, update);
                    break;
                }
            }
        } else if (update.getMessage().hasPhoto()) {

        } else {
            Handler handler = context.getBean(session.getHandler(), Handler.class);
            handler.handle(session, update);
        }
    }
}
