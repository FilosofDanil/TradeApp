package com.example.restapi.controllers;

import com.example.restapi.TestData;
import com.example.restapi.dtos.ItemDTO;
import com.example.restapi.dtos.SettingsDTO;
import com.example.restapi.entites.Item;
import com.example.restapi.entites.ItemType;
import com.example.restapi.entites.TelegramUser;
import com.example.restapi.repositories.ItemRepository;
import com.example.restapi.repositories.ItemTypeRepository;
import com.example.restapi.services.crud.impl.CRUDItemServiceImpl;
import com.example.restapi.services.crud.impl.CRUDItemTypeServiceImpl;
import com.example.restapi.services.itemservice.ItemService;
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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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
class ItemControllerTest {

    private MockMvc mockMvc;

    private final ItemRepository itemRepo = mock(ItemRepository.class);

    @Spy
    private ModelMapper modelMapper = new ModelMapper();

    @Spy
    private CRUDItemServiceImpl crudItemService = new CRUDItemServiceImpl(itemRepo);

    @Spy
    private ItemServiceImpl itemService = new ItemServiceImpl(itemRepo);

    @InjectMocks
    private ItemController itemController;

    private List<Item> data = new ArrayList<>();

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        data.addAll(TestData.items);
        mockMvc = MockMvcBuilders.standaloneSetup(itemController).build();
    }

    @Test
    void getAllItems() throws Exception {
        when(itemRepo.findAll()).thenReturn(data);
        mockMvc.perform(get("/api/v1/items"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(data.get(0).getId().toString())))
                .andExpect(content().string(containsString(data.get(1).getId().toString())));
    }

    @Test
    void getItemById() throws Exception {
        when(itemRepo.existsById(1L)).thenReturn(true);
        when(itemRepo.findById(1L)).thenReturn(java.util.Optional.ofNullable(data.get(0)));
        mockMvc.perform(get("/api/v1/items/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(data.get(0).getId().toString())));
    }

    @Test
    void getAllItemsByUser() throws Exception {
        when(itemRepo.findByUserUsername("few")).thenReturn(List.of(TestData.items.get(0)));
        mockMvc.perform(get("/api/v1/items/user/few"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(data.get(0).getId().toString())));
        mockMvc.perform(get("/api/v1/items/user/error"))
                .andExpect(status().isOk());
    }

    @Test
    void getAllItemsByUserHavingBids() throws Exception {
        when(itemRepo.getItemsByUserHavingBids("few")).thenReturn(List.of(TestData.items.get(0)));
        mockMvc.perform(get("/api/v1/items/user/bids/few"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(data.get(0).getId().toString())));
        mockMvc.perform(get("/api/v1/items/user/bids/error"))
                .andExpect(status().isOk());
    }

    @Test
    void getItemByName() throws Exception {
        when(itemRepo.findByUserTgName("few")).thenReturn(List.of(TestData.items.get(0)));
        mockMvc.perform(get("/api/v1/items/name/few"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(data.get(0).getId().toString())));
        mockMvc.perform(get("/api/v1/items/name/error"))
                .andExpect(status().isOk());
    }

    @Test
    void createItem() throws Exception {
        ItemDTO itemDTO = new ItemDTO(3L, "name", "descr", 100, 200, 1L, 1L, new Date(), new Date(), false);
        when(itemRepo.save(any())).thenAnswer(i -> i.getArguments()[0]);
        String newUserJson = objectMapper.writeValueAsString(itemDTO);
        mockMvc.perform(post("/api/v1/items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newUserJson))
                .andExpect(status().isCreated())
                .andExpect(content().string(containsString(itemDTO.getItemName())));
    }

    @Test
    void updateItem() throws Exception {
        ItemDTO itemDTO = new ItemDTO(2L, "name", "descr", 100, 200, 1L, 1L, new Date(), new Date(), false);
        String newItemJson = objectMapper.writeValueAsString(itemDTO);
        when(itemRepo.existsById(2L)).thenReturn(true);
        when(itemRepo.findById(2L)).thenReturn(Optional.ofNullable(TestData.items.get(1)));
        mockMvc.perform(put("/api/v1/items/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newItemJson))
                .andExpect(status().isOk());
    }

    @Test
    void deleteItem() throws Exception {
        doAnswer(new Answer<Void>() {
            public Void answer(InvocationOnMock invocation) {
                Object[] args = invocation.getArguments();
                data.removeIf(item -> item.getId().equals(1L));
                return null;
            }
        }).when(itemRepo)
                .deleteById(anyLong());
        when(itemRepo.findAll()).thenReturn(data);
        when(itemRepo.existsById(1L)).thenReturn(true);
        mockMvc.perform(delete("/api/v1/items/1"))
                .andExpect(status().isOk());
    }
}