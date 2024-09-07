package com.epam.gym.dao;

import java.util.List;
import java.util.Optional;

public interface BaseDAO<T> {
    void save(T entity, Long id);

    void update(T entity, Long id);

    Optional<T> findById(Long id);

    List<T> findAll();

    void delete(Long id);
}
