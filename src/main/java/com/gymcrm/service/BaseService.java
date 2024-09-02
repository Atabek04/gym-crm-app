package com.gymcrm.service;

import java.util.List;
import java.util.Optional;

public interface BaseService<T> {
    void create(T entity, Integer id);

    void update(T entity, Integer id);

    Optional<T> findById(Integer id);

    List<T> findAll();

    void delete(Integer id);
}
