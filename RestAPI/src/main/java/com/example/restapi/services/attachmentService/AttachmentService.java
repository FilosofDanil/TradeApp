package com.example.restapi.services.attachmentService;

import com.example.restapi.dtos.AttachmentDTO;
import com.example.restapi.entites.Attachment;

import java.util.List;

public interface AttachmentService {
    List<Attachment> getAllByItem(Long itemId);

    List<Attachment> createAttachmentList(List<Attachment> attachments);
}
