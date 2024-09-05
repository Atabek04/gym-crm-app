package com.epam.gym.service;

import com.epam.gym.dao.BaseDAO;

import java.util.List;
import java.util.Optional;

public abstract class AbstractService<T> implements BaseService<T> {
    protected final BaseDAO<T> dao;

    protected AbstractService(BaseDAO<T> dao) {
        this.dao = dao;
    }

    public void create(T entity, Long id) {
        dao.save(entity, id);
    }

    public void update(T entity, Long id) {
        dao.update(entity, id);
    }

    public Optional<T> findById(Long id) {
        return dao.findById(id);
    }

    public List<T> findAll() {
        return dao.findAll();
    }

    public void delete(Long id) {
        dao.delete(id);
    }
}
