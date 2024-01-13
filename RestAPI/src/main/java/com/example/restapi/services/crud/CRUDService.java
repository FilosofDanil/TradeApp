package com.example.restapi.services.crud;

import java.util.List;

public interface CRUDService<T> {
    List<T> getAll();

    T getById(Long id);

    T create(T t);

    void update(Long id, T t);

    void delete(Long id);
}
