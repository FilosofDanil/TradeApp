package com.example.restapi.services.attachmentService.impl;

import com.example.restapi.entites.Attachment;
import com.example.restapi.repositories.AttachmentRepository;
import com.example.restapi.services.attachmentService.AttachmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AttachmentServiceImpl implements AttachmentService {
    private final AttachmentRepository attachmentRepo;

    @Override
    @Transactional
    public List<Attachment> getAllByItem(Long itemId) {
        return attachmentRepo.findByItem(itemId);
    }

    @Override
    @Transactional
    public List<Attachment> createAttachmentList(List<Attachment> attachments) {
        return (List<Attachment>) attachmentRepo.saveAll(attachments);
    }
}
