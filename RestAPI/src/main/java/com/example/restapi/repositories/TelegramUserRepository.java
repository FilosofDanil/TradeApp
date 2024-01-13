package com.example.restapi.repositories;

import com.example.restapi.entites.TelegramUser;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TelegramUserRepository extends CrudRepository<TelegramUser, Long> {
    List<TelegramUser> findAll();

    Optional<TelegramUser> findByTgName(String tgName);

    Optional<TelegramUser> findByUsername(String username);

    @Modifying
    @Query(value = "update TelegramUser set username = :username," +
            " tgName = :tgName, tgSurname = :tgSurname, chatId = :chatId" +
            " where id = :id")
    void updateUser(@Param("username") String username,
                    @Param("tgName") String tgName,
                    @Param("tgSurname") String tgSurname,
                    @Param("chatId") Long chatId,
                    @Param("id") Long id);
}
