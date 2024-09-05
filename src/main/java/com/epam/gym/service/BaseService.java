package com.epam.gym.service;

import java.util.List;
import java.util.Optional;

public interface BaseService<T> {
    void create(T entity, Long id);

    void update(T entity, Long id);

    Optional<T> findById(Long id);

    List<T> findAll();

    void delete(Long id);
}
