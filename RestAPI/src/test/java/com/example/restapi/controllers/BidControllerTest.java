package com.example.restapi.controllers;

import com.example.restapi.TestData;
import com.example.restapi.dtos.BidDTO;
import com.example.restapi.dtos.ItemDTO;
import com.example.restapi.entites.Bid;
import com.example.restapi.entites.Item;
import com.example.restapi.repositories.BidRepository;
import com.example.restapi.repositories.ItemRepository;
import com.example.restapi.services.bidservice.impl.BidServiceImpl;
import com.example.restapi.services.crud.impl.CRUDBidServiceImpl;
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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class BidControllerTest {
    private MockMvc mockMvc;

    private final ItemRepository itemRepo = mock(ItemRepository.class);

    private final BidRepository bidRepo = mock(BidRepository.class);

    @Spy
    private ModelMapper modelMapper = new ModelMapper();

    @Spy
    private CRUDBidServiceImpl crudBidService = new CRUDBidServiceImpl(bidRepo, itemRepo);

    @Spy
    private BidServiceImpl bidService = new BidServiceImpl(bidRepo);

    @InjectMocks
    private BidController bidController;

    private List<Bid> data = new ArrayList<>();

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        data.addAll(TestData.bids);
        mockMvc = MockMvcBuilders.standaloneSetup(bidController).build();
    }

    @Test
    void getAllBids() throws Exception {
        when(bidRepo.findAll()).thenReturn(data);
        mockMvc.perform(get("/api/v1/bids"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(data.get(0).getId().toString())))
                .andExpect(content().string(containsString(data.get(1).getId().toString())));
    }

    @Test
    void getBidById() throws Exception {
        when(bidRepo.existsById(1L)).thenReturn(true);
        when(bidRepo.findById(1L)).thenReturn(java.util.Optional.ofNullable(data.get(0)));
        mockMvc.perform(get("/api/v1/bids/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(data.get(0).getBidPrice().toString())));
    }

    @Test
    void getAllBidsByItem() throws Exception {
        when(bidRepo.findByItem(1L)).thenReturn(List.of(data.get(0)));
        mockMvc.perform(get("/api/v1/bids/item/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(data.get(0).getId().toString())));
    }

    @Test
    void getAllBidsByUser() throws Exception {
        when(bidRepo.findByUser(1L)).thenReturn(List.of(data.get(0)));
        mockMvc.perform(get("/api/v1/bids/user/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(data.get(0).getId().toString())));
    }

    @Test
    void createBid() throws Exception {
        BidDTO bidDTO = new BidDTO(3L, 100, "comment", 1L, 2L);
        when(bidRepo.save(any())).thenAnswer(i -> i.getArguments()[0]);
        when(itemRepo.findById(1L)).thenReturn(Optional.ofNullable(TestData.items.get(0)));
        when(itemRepo.findById(2L)).thenReturn(Optional.ofNullable(TestData.items.get(1)));
        String newBidJson = objectMapper.writeValueAsString(bidDTO);
        mockMvc.perform(post("/api/v1/bids")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newBidJson))
                .andExpect(status().isBadRequest());
        bidDTO.setItemId(1L);
        newBidJson = objectMapper.writeValueAsString(bidDTO);
        mockMvc.perform(post("/api/v1/bids")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newBidJson))
                .andExpect(status().isCreated())
                .andExpect(content().string(containsString(bidDTO.getId().toString())));
    }

    @Test
    void updateBid() throws Exception {
        BidDTO bidDTO = new BidDTO(3L, 100, "comment", 1L, 2L);
        String newBidJson = objectMapper.writeValueAsString(bidDTO);
        when(bidRepo.existsById(2L)).thenReturn(true);
        when(bidRepo.findById(2L)).thenReturn(Optional.ofNullable(TestData.bids.get(1)));
        mockMvc.perform(put("/api/v1/bids/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newBidJson))
                .andExpect(status().isOk());
    }

    @Test
    void deleteBid() throws Exception {
        doAnswer(new Answer<Void>() {
            public Void answer(InvocationOnMock invocation) {
                Object[] args = invocation.getArguments();
                data.removeIf(bid -> bid.getId().equals(1L));
                return null;
            }
        }).when(bidRepo)
                .deleteById(anyLong());
        mockMvc.perform(delete("/api/v1/bids/1"))
                .andExpect(status().isOk());
    }
}