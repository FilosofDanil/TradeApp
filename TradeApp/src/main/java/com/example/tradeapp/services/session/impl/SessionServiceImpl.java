package com.example.tradeapp.services.session.impl;

import com.example.tradeapp.entities.session.UserSession;
import com.example.tradeapp.services.session.SessionService;
import com.example.tradeapp.sessionrepo.UserSessionRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SessionServiceImpl implements SessionService {
    private final UserSessionRepo userSessionRepo;

    @Override
    public UserSession getSession(Long chatId) {
        return userSessionRepo.findById(chatId)
                .orElse(userSessionRepo.save(UserSession.builder()
                        .userData(new HashMap<>())
                        .handler("emptyHandler")
                        .id(chatId)
                        .build()));
    }

    @Override
    public void updateSession(Long chatId, UserSession session) {
        Optional<UserSession> toUpdate = userSessionRepo.findById(chatId);
        if (toUpdate.isEmpty()) {
            userSessionRepo.save(session);
        } else {
            UserSession updateSession = toUpdate.get();
            updateSession.setHandler(session.getHandler());
            updateSession.setUserData(session.getUserData());
            updateSession.setId(session.getId());
            userSessionRepo.save(updateSession);
        }
    }

    @Override
    public void deleteSession(Long chatId) {
        userSessionRepo.deleteById(chatId);
    }
}
