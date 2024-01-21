package com.example.restapi.services.crud.impl;

import com.example.restapi.dtos.TelegramUserDTO;
import com.example.restapi.entites.TelegramUser;
import com.example.restapi.repositories.TelegramUserRepository;
import com.example.restapi.services.crud.CRUDUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CRUDUserServiceImpl implements CRUDUserService {
    private final TelegramUserRepository telegramUserRepo;

    @Override
    public List<TelegramUser> getAll() {
        return telegramUserRepo.findAll();
    }

    @Override
    public TelegramUser getById(Long id) {
        return telegramUserRepo.findById(id).get();
    }

    @Override
    public TelegramUser create(TelegramUser telegramUser) {
        return telegramUserRepo.save(telegramUser);
    }

    @Override
    public void update(Long id, TelegramUser telegramUser) {
        if (!telegramUserRepo.existsById(id)) {
            telegramUserRepo.save(telegramUser);
        } else {
            telegramUserRepo.updateUser(telegramUser.getUsername(),
                    telegramUser.getTgName(), telegramUser.getTgSurname(),
                    telegramUser.getChatId(), id);
        }
    }

    @Override
    public void delete(Long id) {
        telegramUserRepo.deleteById(id);
    }

    private TelegramUser newUser(TelegramUserDTO telegramUserDTO) {
        return TelegramUser.builder()
                .chatId(telegramUserDTO.getChatId())
                .username(telegramUserDTO.getUsername())
                .tgName(telegramUserDTO.getTgSurname())
                .tgSurname(telegramUserDTO.getTgSurname())
                .build();
    }
}
