package com.example.restapi.services.crud.impl;

import com.example.restapi.dtos.AttachmentDTO;
import com.example.restapi.entites.Attachment;
import com.example.restapi.mappers.AttachmentMapper;
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
    public List<AttachmentDTO> getAll() {
        return attachmentRepo.findAll()
                .stream()
                .map(AttachmentMapper::toModel)
                .toList();
    }

    @Override
    public AttachmentDTO getById(Long id) {
        return attachmentRepo.findById(id)
                .map(AttachmentMapper::toModel)
                .get();
    }

    @Override
    public AttachmentDTO create(AttachmentDTO attachmentDTO) {
        return AttachmentMapper.toModel(newAttachment(attachmentDTO));
    }

    @Override
    public void update(Long id, AttachmentDTO attachmentDTO) {
        if (attachmentRepo.findById(id).isEmpty()) {
            newAttachment(attachmentDTO);
        } else {
            attachmentRepo.updateAttachment(attachmentDTO.getItemType(),
                    attachmentDTO.getItemData(), id);
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
