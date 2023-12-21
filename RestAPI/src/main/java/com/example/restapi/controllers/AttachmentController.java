package com.example.restapi.controllers;

import com.example.restapi.dtos.BidDTO;
import com.example.restapi.entites.Attachment;
import com.example.restapi.entites.Bid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/attachments")
@RequiredArgsConstructor
public class AttachmentController {
    @GetMapping("")
    public ResponseEntity<List<Attachment>> getAllAttachments() {
        return null;
    }

    @GetMapping("/item/{itemId}")
    public ResponseEntity<List<Attachment>> getAllAttachmentsByItem(@PathVariable Long itemId) {
        return null;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Attachment> getAttachmentById(@PathVariable Long id) {
        return null;
    }

    @PostMapping("")
    public ResponseEntity<Attachment> createAttachment(@RequestBody Attachment attachment) {
        return null;
    }

    @PutMapping("/{id}")
    public ResponseEntity<HttpStatus> updateAttachment(@RequestBody Attachment attachment, @PathVariable Long id) {
        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteAttachment(@PathVariable Long id) {
        return null;
    }
}
