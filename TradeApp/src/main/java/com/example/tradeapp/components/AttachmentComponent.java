package com.example.tradeapp.components;

import com.example.tradeapp.entities.models.Attachments;
import com.example.tradeapp.entities.models.Items;

import java.util.List;
import java.util.Map;

public interface AttachmentComponent {
    List<Attachments> createAttachment(Map<String, String> data, Long itemId);

    List<Attachments> getAllAttachments(Long itemId);
}
