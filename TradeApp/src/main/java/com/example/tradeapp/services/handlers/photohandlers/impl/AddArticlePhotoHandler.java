package com.example.tradeapp.services.handlers.photohandlers.impl;

import com.example.tradeapp.builder.director.MessageDirector;
import com.example.tradeapp.components.ChatIdFromUpdateComponent;
import com.example.tradeapp.components.impl.TextMessageSender;
import com.example.tradeapp.entities.session.UserSession;
import com.example.tradeapp.services.handlers.photohandlers.PhotoHandler;
import com.example.tradeapp.services.session.SessionService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.Map;

@Component("articleAddPhotos")
@RequiredArgsConstructor
public class AddArticlePhotoHandler implements PhotoHandler {
    private final TextMessageSender textMessageSender;

    private final MessageDirector messageDirector;

    private final SessionService sessionService;

    private final ChatIdFromUpdateComponent updateComponent;

    @Value("${bot.token}")
    private String token;

    private final static String PATH = "https://api.telegram.org/bot";

    private final static String END_PATH = "/getFile?file_id=";

    private final static String DOWNLOAD_SOURCE = "https://api.telegram.org/file/bot";

    @Override
    @SneakyThrows
    public void handle(UserSession session, Update update) {
        String text = "";
        Long chatId = updateComponent.getChatIdFromUpdate(update);
        Map<String, String> data = session.getUserData();
        List<PhotoSize> photoSizes = update.getMessage().getPhoto();
        String response = photoSizes.get(photoSizes.size() - 1).getFileId();
        //form url
        String url = PATH + token + END_PATH + response;
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        HttpEntity<String> httpRequest = new HttpEntity<>(httpHeaders);
        JSONObject jsonObject = new JSONObject(restTemplate.exchange(url, HttpMethod.GET, httpRequest, String.class).getBody());
        String path = String.valueOf(jsonObject.getJSONObject("result").getString("file_path"));
        String source = DOWNLOAD_SOURCE + token + "/" + path;
        URL urlObj = new URL(source);
        try (InputStream is = urlObj.openStream()) {
            byte[] stream = is.readAllBytes();
            FileOutputStream outputStream = new FileOutputStream("TradeApp/src/main/resources/" + path);
            outputStream.write(stream);
            outputStream.close();
            session.setUserData(count(data, path));
            session.setHandler("articleAddPhotos");
            text += "Фото успішно додане";
        } catch (IOException e) {
            e.printStackTrace();
            session.setHandler("articleAddPhotos");
            text += "Щось пішло не так... Спробуйте заново, або перезапустіть бота та повідомте нам про помилку в розділі /support.";
        }
        sessionService.updateSession(chatId, session);
        textMessageSender.sendMessage(messageDirector
                .buildTextMessage(chatId, text));
    }

    private Map<String, String> count(Map<String, String> data, String path) throws IllegalArgumentException {
        Integer count = Integer.parseInt(data.get("articlePhotos"));
        if (count >= 3) {
            throw new IllegalArgumentException("Не можна додавати більше трьох фото!");
        }
        data.put("photo" + count.toString(), path);
        count++;
        data.put("articlePhotos", count.toString());
        return data;
    }
}
