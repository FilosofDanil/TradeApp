package com.example.restapi.services.attachmentService;

import com.example.restapi.dtos.AttachmentDTO;
import com.example.restapi.entites.Attachment;

import java.util.List;

public interface AttachmentService {
    List<AttachmentDTO> getAllByItem(Long itemId);

    List<AttachmentDTO> createAttachmentList(List<Attachment> attachments);
}
