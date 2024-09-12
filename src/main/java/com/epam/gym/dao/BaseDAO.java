package com.epam.gym.dao;

import java.util.List;
import java.util.Optional;

public interface BaseDAO<T> {
    Optional<T> save(T entity);

    Optional<T> update(T entity, Long id);

    Optional<T> findById(Long id);

    List<T> findAll();

    void delete(Long id);
}
