package com.example.restapi.services.crud.impl;

import com.example.restapi.dtos.TelegramUserDTO;
import com.example.restapi.entites.TelegramUser;
import com.example.restapi.mappers.UserMapper;
import com.example.restapi.repositories.TelegramUserRepository;
import com.example.restapi.services.crud.CRUDUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CRUDUserServiceImpl implements CRUDUserService {
    private final TelegramUserRepository telegramUserRepo;

    @Override
    public List<TelegramUserDTO> getAll() {
        return telegramUserRepo.findAll()
                .stream()
                .map(UserMapper::toModel)
                .toList();
    }

    @Override
    public TelegramUserDTO getById(Long id) {
        return telegramUserRepo
                .findById(id).map(UserMapper::toModel).get();
    }

    @Override
    public TelegramUserDTO create(TelegramUserDTO telegramUserDTO) {
        return UserMapper.toModel(telegramUserRepo
                .save(newUser(telegramUserDTO)));
    }

    @Override
    public void update(Long id, TelegramUserDTO telegramUserDTO) {
        if (telegramUserRepo.findById(id).isEmpty()) {
            telegramUserRepo.save(newUser(telegramUserDTO));
        } else {
            telegramUserRepo.updateUser(telegramUserDTO.getUsername(),
                    telegramUserDTO.getTgName(), telegramUserDTO.getTgSurname(),
                    telegramUserDTO.getChatId(), id);
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
