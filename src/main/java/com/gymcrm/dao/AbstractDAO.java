package com.gymcrm.dao;

import org.slf4j.Logger;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public abstract class AbstractDAO<T> {
    protected final Map<Integer, T> storage;
    protected final Logger logger;
    private final String entityName;

    public AbstractDAO(Map<Integer, T> storage, Logger logger, String entityName) {
        this.storage = storage;
        this.logger = logger;
        this.entityName = entityName;
    }

    public void save(T entity, Integer id) {
        storage.put(id, entity);
        logger.info("{} with ID {} has been saved", entityName, id);
    }

    public void update(T entity, Integer id) {
        storage.put(id, entity);
        logger.info("{} with ID {} has been updated", entityName, id);
    }

    public Optional<T> findById(int id) {
        var entity = storage.get(id);
        if (entity == null) {
            logger.debug("{} with ID {} has not been found", entityName, id);
            return Optional.empty();
        }
        logger.info("{} with ID {} has been found", entityName, id);
        return Optional.of(entity);
    }

    public List<T> findAll() {
        var entities = List.copyOf(storage.values());
        if (entities.isEmpty()) {
            logger.info("No {} have been found", entityName);
        } else {
            logger.info("All {} have been found", entityName);
        }
        return entities;
    }

    public void delete(int id) {
        storage.remove(id);
        logger.info("{} with ID {} has been deleted", entityName, id);
    }
}