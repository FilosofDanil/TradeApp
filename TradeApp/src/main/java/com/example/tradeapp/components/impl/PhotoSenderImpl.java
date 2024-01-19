package com.example.tradeapp.components.impl;

import com.example.tradeapp.components.BotSender;
import com.example.tradeapp.components.MessageSender;
import com.example.tradeapp.entities.messages.impl.PhotoMessage;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

import java.io.File;

@Component
@RequiredArgsConstructor
public class PhotoSenderImpl implements MessageSender<PhotoMessage> {
    private final BotSender botSender;

    @Override
    public void sendMessage(PhotoMessage message) {
        try {
            SendPhoto sendMessage = getSendMessage(message.getChatId(),
                    message.getPath(), message.getCaption());
            ReplyKeyboardMarkup markup = message.getMarkup();
            if (markup != null) {
                sendMessage.setReplyMarkup(markup);
            }
            this.botSender.execute(sendMessage);
        } catch (Exception var3) {
            var3.printStackTrace();
        }
    }

    private SendPhoto getSendMessage(Long chatId, String path, String caption) {
        InputFile file = new InputFile(new File(path));
        SendPhoto sendMessage = new SendPhoto();
        sendMessage.setChatId(chatId.toString());
        sendMessage.setPhoto(file);
        sendMessage.setCaption(caption);
        return sendMessage;
    }
}
