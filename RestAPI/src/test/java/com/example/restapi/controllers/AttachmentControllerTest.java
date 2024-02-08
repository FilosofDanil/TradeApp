package com.example.restapi.controllers;

import com.example.restapi.TestData;
import com.example.restapi.dtos.AttachmentDTO;
import com.example.restapi.dtos.BidDTO;
import com.example.restapi.dtos.ItemDTO;
import com.example.restapi.entites.Attachment;
import com.example.restapi.entites.Item;
import com.example.restapi.repositories.AttachmentRepository;
import com.example.restapi.repositories.ItemRepository;
import com.example.restapi.services.attachmentService.impl.AttachmentServiceImpl;
import com.example.restapi.services.crud.impl.CRUDAttachmentServiceImpl;
import com.example.restapi.services.crud.impl.CRUDItemServiceImpl;
import com.example.restapi.services.itemservice.impl.ItemServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import org.modelmapper.ModelMapper;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.*;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class AttachmentControllerTest {

    private MockMvc mockMvc;

    private final AttachmentRepository attachmentRepo = mock(AttachmentRepository.class);

    @Spy
    private ModelMapper modelMapper = new ModelMapper();

    @Spy
    private CRUDAttachmentServiceImpl crudAttachmentService = new CRUDAttachmentServiceImpl(attachmentRepo);

    @Spy
    private AttachmentServiceImpl attachmentService = new AttachmentServiceImpl(attachmentRepo);

    @InjectMocks
    private AttachmentController attachmentController;

    private List<Attachment> data = new ArrayList<>();

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        data.addAll(TestData.attachments);
        mockMvc = MockMvcBuilders.standaloneSetup(attachmentController).build();
    }

    @Test
    void getAllAttachments() throws Exception {
        when(attachmentRepo.findAll()).thenReturn(data);
        mockMvc.perform(get("/api/v1/attachments"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(data.get(0).getId().toString())));
    }

    @Test
    void getAllAttachmentsByItem() throws Exception {
        when(attachmentRepo.findByItem(1L)).thenReturn(List.of(data.get(0)));
        mockMvc.perform(get("/api/v1/attachments/item/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(data.get(0).getId().toString())));
    }

    @Test
    void getAttachmentById() throws Exception {
        when(attachmentRepo.existsById(1L)).thenReturn(true);
        when(attachmentRepo.findById(1L)).thenReturn(java.util.Optional.ofNullable(data.get(0)));
        mockMvc.perform(get("/api/v1/attachments/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(data.get(0).getId().toString())));
    }

    @Test
    void createAttachment() throws Exception {
        AttachmentDTO attachmentDTO = new AttachmentDTO(2L, "name", "rjewkfiko", 2L);
        when(attachmentRepo.save(any())).thenAnswer(i -> i.getArguments()[0]);
        String newUserJson = objectMapper.writeValueAsString(attachmentDTO);
        mockMvc.perform(post("/api/v1/attachments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newUserJson))
                .andExpect(status().isCreated())
                .andExpect(content().string(containsString(attachmentDTO.getItemData())));
    }

    @Test
    void createAttachmentList() throws Exception {
        List<AttachmentDTO> attachmentDTOs = Collections.singletonList(new AttachmentDTO(2L, "name", "rjewkfiko", 2L));
        when(attachmentRepo.saveAll(any())).thenAnswer(i -> i.getArguments()[0]);
        String newUserJson = objectMapper.writeValueAsString(attachmentDTOs);
        mockMvc.perform(post("/api/v1/attachments/list")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newUserJson))
                .andExpect(status().isCreated())
                .andExpect(content().string(containsString(attachmentDTOs.get(0).getItemData())));
    }

    @Test
    void updateAttachment() throws Exception {
        AttachmentDTO attachmentDTO = new AttachmentDTO(2L, "name", "rjewkfiko", 2L);
        String newBidJson = objectMapper.writeValueAsString(attachmentDTO);
        when(attachmentRepo.existsById(1L)).thenReturn(true);
        when(attachmentRepo.findById(1L)).thenReturn(Optional.ofNullable(data.get(0)));
        mockMvc.perform(put("/api/v1/attachments/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newBidJson))
                .andExpect(status().isOk());
    }

    @Test
    void deleteAttachment() throws Exception {
        mockMvc.perform(delete("/api/v1/attachments/1"))
                .andExpect(status().isOk());
    }
}