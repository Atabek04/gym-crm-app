package com.gymcrm.service;

import com.gymcrm.dao.BaseDAO;

import java.util.List;
import java.util.Optional;

public abstract class AbstractService<T> implements BaseService<T> {
    protected final BaseDAO<T> dao;

    protected AbstractService(BaseDAO<T> dao) {
        this.dao = dao;
    }

    public void create(T entity, Integer id) {
        dao.save(entity, id);
    }

    public void update(T entity, Integer id) {
        dao.update(entity, id);
    }

    public Optional<T> findById(Integer id) {
        return dao.findById(id);
    }

    public List<T> findAll() {
        return dao.findAll();
    }

    public void delete(Integer id) {
        dao.delete(id);
    }
}
