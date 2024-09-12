package com.epam.gym.dao.impl;

import com.epam.gym.dao.AbstractDAO;
import com.epam.gym.dao.UserDAO;
import com.epam.gym.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Slf4j
public class UserDAOImpl extends AbstractDAO<User> implements UserDAO {
    public UserDAOImpl() {
        super(User.class);
    }

    @Override
    public User findByUsernameAndPassword(String username, String password) {
        try {
            String hql = "FROM User u WHERE u.username = :username AND u.password = :password";
            return entityManager.createQuery(hql, User.class)
                    .setParameter("username", username)
                    .setParameter("password", password)
                    .getSingleResult();
        } catch (Exception e) {
            log.error("UserDAOImpl:: Error changing user's password.");
            return null;
        }
    }

    @Override
    public void changeUserPassword(String username, String newPassword) {
        try {
            String hql = "UPDATE User u SET u.password = :password WHERE u.username = :username";
            entityManager.createQuery(hql)
                    .setParameter("username", username)
                    .setParameter("password", newPassword)
                    .executeUpdate();
        } catch (Exception e) {
            log.error("UserDAOImpl:: Error updating password of {}: {}", username, e.getMessage());
        }
    }

    @Override
    public Optional<User> findById(Long id) {
        try {
            return Optional.ofNullable(entityManager.find(User.class, id));
        } catch (Exception e) {
            log.error("UserDAOImpl:: Error finding user with id {}: {}", id, e.getMessage());
            return Optional.empty();
        }
    }
}