package com.example.restapi.controllers;

import com.example.restapi.TestData;
import com.example.restapi.dtos.SettingsDTO;
import com.example.restapi.entites.Settings;
import com.example.restapi.entites.TelegramUser;
import com.example.restapi.repositories.ItemTypeRepository;
import com.example.restapi.repositories.SettingsRepository;
import com.example.restapi.repositories.TelegramUserRepository;
import com.example.restapi.services.crud.CRUDService;
import com.example.restapi.services.crud.impl.CRUDSettingsServiceImpl;
import com.example.restapi.services.crud.impl.CRUDUserServiceImpl;
import com.example.restapi.services.settingsservice.impl.SettingsServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
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
import java.util.Optional;
import java.util.Set;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class SettingsControllerTest {

    private MockMvc mockMvc;

    @Spy
    private ModelMapper modelMapper = new ModelMapper();

    private final ItemTypeRepository itemTypeRepo = mock(ItemTypeRepository.class);

    private final TelegramUserRepository telegramUserRepo = mock(TelegramUserRepository.class);

    private final CRUDUserServiceImpl userCRUDService = new CRUDUserServiceImpl(telegramUserRepo);

    private final SettingsRepository settingsRepository = mock(SettingsRepository.class);

    @Spy
    private SettingsServiceImpl settingsService = new SettingsServiceImpl(settingsRepository, itemTypeRepo, userCRUDService);

    @Spy
    private CRUDSettingsServiceImpl crudSettingsService = new CRUDSettingsServiceImpl(settingsRepository);

    @InjectMocks
    private SettingsController settingsController;

    private List<Settings> data = new ArrayList<>();

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        data.addAll(TestData.settings);
        mockMvc = MockMvcBuilders.standaloneSetup(settingsController).build();
    }

    @Test
    void getAllSettings() throws Exception {
        when(settingsRepository.findAll()).thenReturn(data);
        mockMvc.perform(get("/api/v1/settings"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("\"userId\":" +
                        data.get(0).getUser().getId().toString())))
                .andExpect(content().string(containsString(data.get(1).getId().toString())));
    }

    @Test
    void getSettingsById() throws Exception {
        when(settingsRepository.existsById(1L)).thenReturn(true);
        when(settingsRepository.existsById(2L)).thenReturn(true);
        when(settingsRepository.findById(1L)).thenReturn(Optional.ofNullable(data.get(0)));
        when(settingsRepository.findById(2L)).thenReturn(Optional.ofNullable(data.get(1)));
        mockMvc.perform(get("/api/v1/settings/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(data.get(0).getId().toString())));
        mockMvc.perform(get("/api/v1/settings/2"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(data.get(1).getId().toString())));
        mockMvc.perform(get("/api/v1/settings/3"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getSettingsByUsername() throws Exception {
        when(settingsRepository.findByUserName("few"))
                .thenReturn(data.stream()
                        .filter(settings -> settings.getUser().getUsername().equals("few"))
                        .findFirst().get());
        mockMvc.perform(get("/api/v1/settings/user/few"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(data.get(0).getId().toString())));
        mockMvc.perform(get("/api/v1/settings/user/error"))
                .andExpect(status().isNotFound());
    }

    @Test
    void createSettings() throws Exception {
        SettingsDTO newSettings = new SettingsDTO(3L, "Житомир", 1L, List.of("Авто"));
        String newSettingsJson = objectMapper.writeValueAsString(newSettings);
        when(settingsRepository.save(any())).thenAnswer(i -> i.getArguments()[0]);
        when(itemTypeRepo.findByName(anyString())).thenReturn(TestData.itemTypes.get(0));
        when(telegramUserRepo.existsById(1L)).thenReturn(true);
        when(telegramUserRepo.findById(1L)).thenReturn(Optional.ofNullable(TestData.users.get(0)));
        mockMvc.perform(post("/api/v1/settings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newSettingsJson))
                .andExpect(status().isCreated())
                .andExpect(content().string(containsString(newSettings.getUserId().toString())));

    }

    @Test
    void updateSettings() throws Exception {
        SettingsDTO updateSettings = new SettingsDTO(3L, "Житомир", 2L, List.of("Авто"));
        String newSettingsJson = objectMapper.writeValueAsString(updateSettings);
        when(settingsRepository.existsById(1L)).thenReturn(true);
        when(settingsRepository.findById(1L)).thenReturn(Optional.ofNullable(TestData.settings.get(0)));
        when(settingsRepository.save(any())).thenAnswer(i -> i.getArguments()[0]);
        when(itemTypeRepo.findByName(anyString())).thenReturn(TestData.itemTypes.get(0));
        when(telegramUserRepo.existsById(2L)).thenReturn(true);
        when(telegramUserRepo.findById(2L)).thenReturn(Optional.ofNullable(TestData.users.get(1)));
        mockMvc.perform(put("/api/v1/settings/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newSettingsJson))
                .andExpect(status().isOk());
    }

    @Test
    void deleteSettings() throws Exception {
        doAnswer(new Answer<Void>() {
            public Void answer(InvocationOnMock invocation) {
                Object[] args = invocation.getArguments();
                data.removeIf(settings -> settings.getId().equals(1L));
                return null;
            }
        }).when(settingsRepository)
                .deleteById(anyLong());
        when(settingsRepository.findAll()).thenReturn(data);
        when(settingsRepository.existsById(1L)).thenReturn(true);
        mockMvc.perform(delete("/api/v1/settings/1"))
                .andExpect(status().isOk());
    }
}