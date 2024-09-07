package com.epam.gym.dao;

import org.slf4j.Logger;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public abstract class AbstractDAO<T> implements BaseDAO<T> {
    protected final Map<Long, T> storage;
    protected final Logger logger;
    private final String entityName;

    protected AbstractDAO(Map<Long, T> storage, Logger logger, String entityName) {
        this.storage = storage;
        this.logger = logger;
        this.entityName = entityName;
    }

    @Override
    public void save(T entity, Long id) {
        storage.put(id, entity);
        logger.info("{} with ID {} has been saved", entityName, id);
    }

    @Override
    public void update(T entity, Long id) {
        storage.put(id, entity);
        logger.info("{} with ID {} has been updated", entityName, id);
    }

    @Override
    public Optional<T> findById(Long id) {
        var entity = storage.get(id);
        if (entity == null) {
            logger.debug("{} with ID {} has not been found", entityName, id);
            return Optional.empty();
        }
        logger.info("{} with ID {} has been found", entityName, id);
        return Optional.of(entity);
    }

    @Override
    public List<T> findAll() {
        var entities = List.copyOf(storage.values());
        if (entities.isEmpty()) {
            logger.info("No {} have been found", entityName);
        } else {
            logger.info("All {} have been found", entityName);
        }
        return entities;
    }

    @Override
    public void delete(Long id) {
        storage.remove(id);
        logger.info("{} with ID {} has been deleted", entityName, id);
    }
}