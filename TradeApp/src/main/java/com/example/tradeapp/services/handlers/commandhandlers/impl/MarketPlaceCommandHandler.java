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

import java.util.List;

@Component
@RequiredArgsConstructor
public class MarketPlaceCommandHandler implements CommandHandler {
    private final static String command = "/marketplace";

    private final MessageSender<TextMessage> textMessageMessageSender;

    private final MessageDirector messageDirector;

    private final SessionService sessionService;

    private final ChatIdFromUpdateComponent updateComponent;

    @Override
    public void handle(UserSession session, Update update) {
        Long chatId = updateComponent.getChatIdFromUpdate(update);
        String text = "Вітаємо вас на маркетплейсі! \uD83C\uDF81\uD83C\uDF81\uD83C\uDF81  \n\n" +
                "Для подальшого користування оберіть будь-ласка ваше місто, та вкажіть категорії товарів які бажаєте купувати! \n\n" +
                "Ви можете змінити ці параметри в будь-який час. ⚙️\n" +
                "Також ви можете розміщувати і власні товари \uD83D\uDCB0, але перед цим вам необхідно буде ознайомитись з правилами їх розміщення \n" +
                "Для цього вам необхідно перейти в розділ Мої Товари " +
                "Використовуйте для цього відповідні кнопки знизу!\n" +
                "⬇️⬇️⬇️⬇️⬇️⬇️⬇️⬇️⬇️⬇️⬇️⬇️";
        List<String> rows = List.of("⚙ Змінити/Встановити налаштування",
                "\uD83D\uDC41 Перегляд товарів", "\uD83D\uDE9B Мої Товари",
                "\uD83D\uDCC8 Мої ставки", "\uD83D\uDCD4 Історія покупок/продажів",
                "\uD83D\uDD19 Повернутись назад");
        textMessageMessageSender.sendMessage(messageDirector
                .buildTextMessageWithInlineKeyboard(chatId, text, rows));
        session.setHandler("market");
        sessionService.updateSession(chatId, session);
    }

    @Override
    public String getCommand() {
        return command;
    }
}
