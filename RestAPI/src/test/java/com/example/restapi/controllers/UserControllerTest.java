package com.example.restapi.controllers;

import com.example.restapi.TestData;
import com.example.restapi.entites.TelegramUser;
import com.example.restapi.repositories.TelegramUserRepository;
import com.example.restapi.services.crud.impl.CRUDUserServiceImpl;
import com.example.restapi.services.userservice.impl.UserServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.catalina.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import org.modelmapper.ModelMapper;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Spy
    private ModelMapper modelMapper = new ModelMapper();

    private final TelegramUserRepository userRepository = mock(TelegramUserRepository.class);

    @Spy
    private CRUDUserServiceImpl crudService = new CRUDUserServiceImpl(userRepository);

    @Spy
    private UserServiceImpl userService = new UserServiceImpl(userRepository);

    @InjectMocks
    private UserController userController;

    private MockMvc mockMvc;

    private List<TelegramUser> data = new ArrayList<>();

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        data.addAll(TestData.users);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    void getAllUsers() throws Exception {
        when(userRepository.findAll()).thenReturn(data);
        mockMvc.perform(get("/api/v1/users"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(data.get(0).getTgName())))
                .andExpect(content().string(containsString(data.get(1).getId().toString())));
    }

    @Test
    void getUserByUsername() throws Exception {
        when(userRepository.findByUsername("few"))
                .thenReturn(data.stream().filter(user -> user.getUsername().equals("few")).findFirst());
        mockMvc.perform(get("/api/v1/users/username/few"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(data.get(0).getUsername())));
        mockMvc.perform(get("/api/v1/users/username/error"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getUserByTgName() throws Exception {
        when(userRepository.findByTgName("name"))
                .thenReturn(data.stream().filter(user -> user.getTgName().equals("name")).findFirst());
        mockMvc.perform(get("/api/v1/users/tgName/name"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(data.get(0).getTgName())));
        mockMvc.perform(get("/api/v1/users/tgName/error"))
                .andExpect(status().isNotFound());
    }

    @Test
    void checkUserByUsername() throws Exception {
        when(userRepository.findByUsername("few"))
                .thenReturn(data.stream().filter(user -> user.getUsername().equals("few")).findFirst());
        mockMvc.perform(get("/api/v1/users/check/username/few"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("true")));
        mockMvc.perform(get("/api/v1/users/check/username/error"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("false")));
    }

    @Test
    void checkUserByTgName() throws Exception {
        when(userRepository.findByTgName("name"))
                .thenReturn(data.stream().filter(user -> user.getTgName().equals("name")).findFirst());
        mockMvc.perform(get("/api/v1/users/check/tgName/name"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("true")));
        mockMvc.perform(get("/api/v1/users/check/tgName/error"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("false")));
    }

    @Test
    void getUserById() throws Exception {
        when(userRepository.existsById(1L)).thenReturn(true);
        when(userRepository.existsById(2L)).thenReturn(true);
        when(userRepository.findById(1L)).thenReturn(Optional.ofNullable(data.get(0)));
        when(userRepository.findById(2L)).thenReturn(Optional.ofNullable(data.get(1)));
        mockMvc.perform(get("/api/v1/users/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(data.get(0).getId().toString())));
        mockMvc.perform(get("/api/v1/users/2"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(data.get(1).getId().toString())));
        mockMvc.perform(get("/api/v1/users/3"))
                .andExpect(status().isNotFound());

    }

    @Test
    void createUser() throws Exception {
        TelegramUser newUser = TelegramUser.builder()
                .id(3L)
                .username("username")
                .chatId(12312L)
                .tgName("newName")
                .tgSurname("newSurname")
                .build();
        String newUserJson = objectMapper.writeValueAsString(newUser);
        when(userRepository.save(any())).thenReturn(newUser);
        mockMvc.perform(post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newUserJson))
                .andExpect(status().isCreated())
                .andExpect(content().string(containsString(newUser.getUsername())));
    }

    @Test
    void updateUser() throws Exception {
        TelegramUser updatedUser = TelegramUser.builder()
                .id(1L)
                .username("updated")
                .chatId(10000L)
                .tgName("updated")
                .tgSurname("updated")
                .build();
        String newUserJson = objectMapper.writeValueAsString(updatedUser);
        doAnswer(new Answer<Void>() {
            public Void answer(InvocationOnMock invocation) {
                Object[] args = invocation.getArguments();
                TelegramUser user = data.stream()
                        .filter(telegramUser -> telegramUser.getId()
                                .equals(1L)).findFirst().get();
                data.removeIf(telegramUser -> telegramUser.getId().equals(1L));
                user = updatedUser;
                data.add(user);
                return null;
            }
        }).when(userRepository)
                .updateUser(anyString(), anyString(), anyString(), anyLong(), anyLong());
        when(userRepository.findAll()).thenReturn(data);
        when(userRepository.existsById(1L)).thenReturn(true);
        mockMvc.perform(put("/api/v1/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newUserJson))
                .andExpect(status().isOk());
        mockMvc.perform(get("/api/v1/users"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(updatedUser.getId().toString())))
                .andExpect(content().string(containsString(updatedUser.getTgName())));

    }

    @Test
    void deleteUser() throws Exception {
        doAnswer(new Answer<Void>() {
            public Void answer(InvocationOnMock invocation) {
                Object[] args = invocation.getArguments();
                data.removeIf(telegramUser -> telegramUser.getId().equals(1L));
                return null;
            }
        }).when(userRepository)
                .deleteById(anyLong());
        when(userRepository.findAll()).thenReturn(data);
        when(userRepository.existsById(1L)).thenReturn(true);
        mockMvc.perform(delete("/api/v1/users/1"))
                .andExpect(status().isOk());
    }
}