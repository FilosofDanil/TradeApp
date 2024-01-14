package com.example.restapi.controllers;

import com.example.restapi.dtos.AttachmentDTO;
import com.example.restapi.entites.Attachment;
import com.example.restapi.services.attachmentService.AttachmentService;
import com.example.restapi.services.crud.CRUDService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/attachments")
@RequiredArgsConstructor
public class AttachmentController {
    private final CRUDService<Attachment> crudService;

    private final AttachmentService attachmentService;

    private final ModelMapper modelMapper;

    @GetMapping("")
    public ResponseEntity<List<AttachmentDTO>> getAllAttachments() {
        return ResponseEntity.ok(crudService.getAll()
                .stream()
                .map(this::toDto)
                .toList());
    }

    @GetMapping("/item/{itemId}")
    public ResponseEntity<List<AttachmentDTO>> getAllAttachmentsByItem(@PathVariable Long itemId) {
        return ResponseEntity.ok(attachmentService.getAllByItem(itemId)
                .stream()
                .map(this::toDto)
                .toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AttachmentDTO> getAttachmentById(@PathVariable Long id) {
        return ResponseEntity.ok(toDto(crudService.getById(id)));
    }

    @PostMapping("")
    public ResponseEntity<AttachmentDTO> createAttachment(@RequestBody AttachmentDTO attachment) {
        return new ResponseEntity<>(toDto(crudService.create(toEntity(attachment))), HttpStatus.CREATED);
    }

    @PostMapping("/list")
    public ResponseEntity<List<AttachmentDTO>> createAttachmentList(@RequestBody List<AttachmentDTO> attachments) {
        return new ResponseEntity<>(attachmentService.createAttachmentList(attachments.stream()
                        .map(this::toEntity)
                        .toList())
                .stream()
                .map(this::toDto)
                .toList(),
                HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HttpStatus> updateAttachment(@RequestBody AttachmentDTO attachment, @PathVariable Long id) {
        crudService.update(id, toEntity(attachment));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteAttachment(@PathVariable Long id) {
        crudService.delete(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    private AttachmentDTO toDto(Attachment attachment) {
        return modelMapper.map(attachment, AttachmentDTO.class);
    }

    private Attachment toEntity(AttachmentDTO attachmentDTO) {
        return modelMapper.map(attachmentDTO, Attachment.class);
    }
}
