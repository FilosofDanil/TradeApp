package com.example.restapi.services.crud.impl;

import com.example.restapi.dtos.AttachmentDTO;
import com.example.restapi.entites.Attachment;
import com.example.restapi.repositories.AttachmentRepository;
import com.example.restapi.services.crud.CRUDAttachmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CRUDAttachmentServiceImpl implements CRUDAttachmentService {
    private final AttachmentRepository attachmentRepo;

    @Override
    public List<Attachment> getAll() {
        return attachmentRepo.findAll();
    }

    @Override
    public Attachment getById(Long id) {
        return attachmentRepo.findById(id).get();
    }

    @Override
    public Attachment create(Attachment attachment) {
        return attachmentRepo.save(attachment);
    }

    @Override
    public void update(Long id, Attachment attachment) {
        if (attachmentRepo.existsById(id)) {
            attachmentRepo.save(attachment);
        } else {
            attachmentRepo.updateAttachment(attachment.getItemType(),
                    attachment.getItemData(), id);
        }
    }

    @Override
    public void delete(Long id) {
        attachmentRepo.deleteById(id);
    }

    private Attachment newAttachment(AttachmentDTO attachmentDTO) {
        return attachmentRepo.create(attachmentDTO.getItemId(),
                attachmentDTO.getItemType(),
                attachmentDTO.getItemData());
    }
}
