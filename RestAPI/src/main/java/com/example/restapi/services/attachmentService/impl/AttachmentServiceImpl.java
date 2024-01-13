package com.example.restapi.services.attachmentService.impl;

import com.example.restapi.dtos.AttachmentDTO;
import com.example.restapi.entites.Attachment;
import com.example.restapi.mappers.AttachmentMapper;
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
    public List<AttachmentDTO> getAllByItem(Long itemId) {
        return attachmentRepo.findByItem(itemId)
                .stream()
                .map(AttachmentMapper::toModel)
                .toList();
    }

    @Override
    @Transactional
    public List<AttachmentDTO> createAttachmentList(List<Attachment> attachments) {
        attachmentRepo.saveAll(attachments);
        return attachments.stream()
                .map(AttachmentMapper::toModel)
                .toList();
    }
}
