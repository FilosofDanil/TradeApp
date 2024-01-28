package com.example.tradeapp.components.impl;

import com.example.tradeapp.client.AttachmentClient;
import com.example.tradeapp.components.AttachmentComponent;
import com.example.tradeapp.entities.models.Attachments;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class AttachmentComponentImpl implements AttachmentComponent {
    private final AttachmentClient attachmentClient;

    @Override
    public List<Attachments> createAttachment(Map<String, String> data, Long itemId) {
        List<Attachments> attachments = new ArrayList<>();
        for (int i = 0; i < getPhotoCount(data); i++) {
            Attachments attachment = Attachments.builder()
                    .itemId(itemId)
                    .itemData(data.get("photo" + i))
                    .itemType("img")
                    .build();
            attachments.add(attachment);
        }
        attachmentClient.createAttachmentList(attachments);
        return attachments;
    }

    @Override
    public List<Attachments> getAllAttachments(Long itemId) {
        return attachmentClient.getAllAttachmentsByItem(itemId);
    }

    private Integer getPhotoCount(Map<String, String> data) {
        return Integer.parseInt(data.get("articlePhotos"));
    }
}
