package com.gymcrm.dao;

import java.util.List;
import java.util.Optional;

public interface BaseDAO<T> {
    void save(T entity, Integer id);

    void update(T entity, Integer id);

    Optional<T> findById(int id);

    List<T> findAll();

    void delete(int id);
}
