package com.epam.gym.dao.impl;

import com.epam.gym.dao.AbstractDAO;
import com.epam.gym.dao.UserDAO;
import com.epam.gym.model.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Slf4j
public class UserDAOImpl extends AbstractDAO<User> implements UserDAO {

    @PersistenceContext
    private EntityManager entityManager;

    public UserDAOImpl() {
        super(User.class);
    }

    @Override
    public User findByUsername(String username, String password) {
        try {
            String hql = "FROM User u WHERE u.username = :username AND u.password = :password";
            return entityManager.createQuery(hql, User.class)
                    .setParameter("username", username)
                    .setParameter("password", password)
                    .getSingleResult();
        } catch (Exception e) {
            log.error("Error finding user by username and password: {}", e.getMessage());
            return null;
        }
    }

    @Override
    public void changePassword(String username, String newPassword) {
        try {
            String hql = "UPDATE User u SET u.password = :password WHERE u.username = :username";
            entityManager.createQuery(hql)
                    .setParameter("username", username)
                    .setParameter("password", newPassword)
                    .executeUpdate();
        } catch (Exception e) {
            log.error("Error updating password of {}: {}", username, e.getMessage());
        }
    }

    @Override
    public Optional<User> findById(Long id) {
        try {
            return Optional.ofNullable(entityManager.find(User.class, id));
        } catch (Exception e) {
            log.error("Error finding user with id {}: {}", id, e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public void activateUser(String username) {
        try {
            String hql = "UPDATE User u SET u.isActive = true WHERE u.username = :username AND u.isActive = false";
            int updatedCount = entityManager.createQuery(hql)
                    .setParameter("username", username)
                    .executeUpdate();

            if (updatedCount > 0) {
                log.info("User {} has been activated.", username);
            } else {
                log.info("User {} was already active or does not exist.", username);
            }
        } catch (Exception e) {
            log.error("Error activating user {}: {}", username, e.getMessage());
        }
    }

    @Override
    public void deactivateUser(String username) {
        try {
            String hql = "UPDATE User u SET u.isActive = false WHERE u.username = :username AND u.isActive = true";
            int updatedCount = entityManager.createQuery(hql)
                    .setParameter("username", username)
                    .executeUpdate();

            if (updatedCount > 0) {
                log.info("User {} has been deactivated.", username);
            } else {
                log.info("User {} was already inactive or does not exist.", username);
            }
        } catch (Exception e) {
            log.error("Error deactivating user {}: {}", username, e.getMessage());
        }
    }

    @Override
    public Optional<User> findUserByUsername(String username) {
        try {
            String hql = "FROM User u WHERE u.username = :username";
            return Optional.ofNullable(entityManager.createQuery(hql, User.class)
                    .setParameter("username", username)
                    .getSingleResult());
        } catch (Exception e) {
            log.error("Error finding user with username {}: {}", username, e.getMessage());
            return Optional.empty();
        }
    }
}