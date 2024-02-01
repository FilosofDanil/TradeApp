package com.example.tradeapp.services.handlers.texthandlers.impl.articles;

import com.example.tradeapp.builder.director.MessageDirector;
import com.example.tradeapp.components.BidListFormer;
import com.example.tradeapp.components.ChatIdFromUpdateComponent;
import com.example.tradeapp.components.impl.TextMessageSender;
import com.example.tradeapp.entities.session.UserSession;
import com.example.tradeapp.services.handlers.texthandlers.TextHandler;
import com.example.tradeapp.services.session.SessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;
import java.util.Map;

@Component("itemMenu")
@RequiredArgsConstructor
public class ItemMenuTextHandler implements TextHandler {
    private final TextMessageSender textMessageSender;

    private final MessageDirector messageDirector;

    private final SessionService sessionService;

    private final ChatIdFromUpdateComponent updateComponent;

    private final BidListFormer bidListFormer;

    @Override
    public void handle(UserSession session, Update update) {
        String text = "";
        String message = update.getMessage().getText();
        Long chatId = updateComponent.getChatIdFromUpdate(update);
        Map<String, String> data = session.getUserData();
        if (message.equals("Подовжити термін продажу")) {
            text += "Добре! Тепер введіть будь-ласка нову дату експірації до якої бажаєте продати товар" +
                    "\nБудьте уважні із форматом вводу дати, вводьте її наступним чином: dd-mm-yyyy";
            session.setHandler("itemExtend");
        } else if (message.equals("Переглянути список охочих купити товар")) {
            text += "Ось всі наявні ставки, згідно вашого товару: \n";
            Long itemId = Long.parseLong(data.get("itemId"));
            text += bidListFormer.fromResponseBidList(itemId);
            session.setHandler("showBids");
        } else if (message.equals("Видалити товар")) {
            text += "Ви впевнені, що хочете видалити обраний товар?";
            List<String> rows = List.of("Так", "Ні");
            session.setHandler("confirmDeleting");
            sessionService.updateSession(chatId, session);
            textMessageSender.sendMessage(messageDirector
                    .buildTextMessageWithReplyKeyboard(chatId, text, rows));
            return;
        } else {
            text += "Нема такого варіант відповіді!";
            session.setHandler("itemMenu");
        }
        sessionService.updateSession(chatId, session);
        textMessageSender.sendMessage(messageDirector
                .buildTextMessage(chatId, text));
    }
}
