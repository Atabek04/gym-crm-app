package com.epam.gym.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.stereotype.Repository;

import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Repository
@RequiredArgsConstructor
public abstract class AbstractDAO<T> implements BaseDAO<T> {

    private final Class<T> entityClass;

    @PersistenceContext
    protected EntityManager entityManager;

    @Override
    public Optional<T> save(T entity) {
        try {
            entityManager.clear();
            entityManager.persist(entity);
            entityManager.flush();
            log.info("{} has been saved", entityClass.getSimpleName());
            return Optional.ofNullable(entity);
        } catch (Exception e) {
            log.error("Error saving entity: {}", e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public Optional<T> update(T entity, Long id) {
        try {
            T existingEntity = entityManager.find(entityClass, id);
            if (existingEntity != null) {
                copyNonNullProperties(entity, existingEntity);
                entityManager.merge(existingEntity);
                log.info("{} with ID {} has been updated", entityClass.getSimpleName(), id);
                return Optional.of(existingEntity);
            } else {
                log.warn("Entity with ID {} not found", id);
            }
            return Optional.empty();
        } catch (Exception e) {
            log.error("Error updating entity: {}", e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public Optional<T> findById(Long id) {
        try {
            T entity = entityManager.find(entityClass, id);
            if (entity != null) {
                log.info("{} with ID {} has been found", entityClass.getSimpleName(), id);
                return Optional.of(entity);
            } else {
                log.debug("{} with ID {} has not been found", entityClass.getSimpleName(), id);
                return Optional.empty();
            }
        } catch (Exception e) {
            log.error("Error finding entity by ID: {}", e.getMessage(), e);
            return Optional.empty();
        }
    }

    @Override
    public List<T> findAll() {
        try {
            var hql = String.format("FROM %s", entityClass.getSimpleName());
            return entityManager.createQuery(hql, entityClass).getResultList();
        } catch (Exception e) {
            log.error("Error finding all entities: {}", e.getMessage(), e);
            return List.of();
        }
    }

    @Override
    public void delete(Long id) {
        try {
            T entity = entityManager.find(entityClass, id);
            if (entity != null) {
                entityManager.remove(entity);
                log.info("{} with ID {} has been deleted", entityClass.getSimpleName(), id);
            } else {
                log.warn("{} with ID {} does not exist, cannot delete", entityClass.getSimpleName(), id);
            }
        } catch (Exception e) {
            log.error("Error deleting entity: {}", e.getMessage(), e);
            throw e;
        }
    }

    private void copyNonNullProperties(T source, T target) {
        BeanUtils.copyProperties(source, target, getNullPropertyNames(source));
    }

    private String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<>();
        for (PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) emptyNames.add(pd.getName());
        }
        return emptyNames.toArray(new String[0]);
    }
}