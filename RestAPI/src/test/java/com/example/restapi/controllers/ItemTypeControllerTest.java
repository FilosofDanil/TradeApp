package com.example.restapi.controllers;

import com.example.restapi.TestData;
import com.example.restapi.entites.ItemType;
import com.example.restapi.entites.Settings;
import com.example.restapi.entites.TelegramUser;
import com.example.restapi.repositories.ItemTypeRepository;
import com.example.restapi.repositories.SettingsRepository;
import com.example.restapi.services.crud.impl.CRUDItemTypeServiceImpl;
import com.example.restapi.services.crud.impl.CRUDSettingsServiceImpl;
import com.example.restapi.services.crud.impl.CRUDUserServiceImpl;
import com.example.restapi.services.settingsservice.impl.SettingsServiceImpl;
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

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class ItemTypeControllerTest {
    private MockMvc mockMvc;

    @Spy
    private ModelMapper modelMapper = new ModelMapper();

    private final ItemTypeRepository itemTypeRepo = mock(ItemTypeRepository.class);

    @Spy
    private CRUDItemTypeServiceImpl crudItemTypeService = new CRUDItemTypeServiceImpl(itemTypeRepo);

    @InjectMocks
    private ItemTypeController itemTypeController;

    private List<ItemType> data = new ArrayList<>();

    private ObjectMapper objectMapper;
    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        data.addAll(TestData.itemTypes);
        mockMvc = MockMvcBuilders.standaloneSetup(itemTypeController).build();
    }

    @Test
    void getAllItemTypes() throws Exception {
        when(itemTypeRepo.findAll()).thenReturn(data);
        mockMvc.perform(get("/api/v1/itemTypes"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(data.get(0).getId().toString())));
    }

    @Test
    void getItemTypeById() throws Exception {
        when(itemTypeRepo.existsById(1L)).thenReturn(true);
        when(itemTypeRepo.findById(1L)).thenReturn(java.util.Optional.ofNullable(data.get(0)));
        mockMvc.perform(get("/api/v1/itemTypes/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(data.get(0).getId().toString())));
    }

    @Test
    void createItemType() throws Exception {
        ItemType newType = ItemType.builder()
                .id(2L)
                .name("newName")
                .build();
        String newUserJson = objectMapper.writeValueAsString(newType);
        when(itemTypeRepo.save(any())).thenReturn(newType);
        mockMvc.perform(post("/api/v1/itemTypes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newUserJson))
                .andExpect(status().isCreated())
                .andExpect(content().string(containsString(newType.getId().toString())));
    }

    @Test
    void updateItemType() throws Exception {
        ItemType newType = ItemType.builder()
                .id(2L)
                .name("newName")
                .build();
        String newUserJson = objectMapper.writeValueAsString(newType);
        when(itemTypeRepo.save(any())).thenReturn(newType);
        when(itemTypeRepo.existsById(1L)).thenReturn(true);
        when(itemTypeRepo.findById(1L)).thenReturn(java.util.Optional.ofNullable(data.get(0)));
        when(itemTypeRepo.findAll()).thenReturn(data);
        mockMvc.perform(put("/api/v1/itemTypes/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newUserJson))
                .andExpect(status().isOk());
    }

    @Test
    void deleteItemType() throws Exception {
        doAnswer(new Answer<Void>() {
            public Void answer(InvocationOnMock invocation) {
                Object[] args = invocation.getArguments();
                data.removeIf(itemType -> itemType.getId().equals(1L));
                return null;
            }
        }).when(itemTypeRepo)
                .deleteById(anyLong());
        mockMvc.perform(delete("/api/v1/itemTypes/1"))
                .andExpect(status().isOk());
    }
}