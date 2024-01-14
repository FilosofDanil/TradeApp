package com.example.tradeapp.client;

import com.example.tradeapp.entities.models.Attachments;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "attachmentClient", url = "${application.config.attachments}")
public interface AttachmentClient {
    @GetMapping("")
    List<Attachments> getAllAttachments();

    @GetMapping("/item/{itemId}")
    List<Attachments> getAllAttachmentsByItem(@PathVariable Long itemId);

    @GetMapping("/{id}")
    Attachments getAttachmentById(@PathVariable Long id);

    @PostMapping("")
    Attachments createAttachment(@RequestBody Attachments attachment);

    @PostMapping("/list")
    List<Attachments> createAttachmentList(@RequestBody List<Attachments> attachments);

    @PutMapping("/{id}")
    void updateAttachment(@RequestBody Attachments attachment, @PathVariable Long id);

    @DeleteMapping("/{id}")
    void deleteAttachment(@PathVariable Long id);
}
