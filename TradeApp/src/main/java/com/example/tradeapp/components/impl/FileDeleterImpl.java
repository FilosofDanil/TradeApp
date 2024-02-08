package com.example.tradeapp.components.impl;

import com.example.tradeapp.components.FileDeleter;
import com.example.tradeapp.entities.models.Attachments;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class FileDeleterImpl implements FileDeleter {
    private final static String LOCAL_STORAGE_PATH = "TradeApp/src/main/resources/";

    @Override
    public void deleteFile(Attachments attachment) {
        File file = new File(LOCAL_STORAGE_PATH + attachment.getItemData());
        file.delete();
    }
}
