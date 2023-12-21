package com.example.tradeapp.sessionrepo;

import com.example.tradeapp.entities.session.UserSession;
import org.springframework.data.repository.CrudRepository;

public interface UserSessionRepo extends CrudRepository<UserSession, Long> {
}
