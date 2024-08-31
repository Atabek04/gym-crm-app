package com.gymcrm.dao.impl;

import com.gymcrm.dao.UserDAO;
import com.gymcrm.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class UserDAOImpl implements UserDAO {
    private static final Logger logger = LoggerFactory.getLogger(UserDAOImpl.class);
    private final Map<Integer, User> userStorage;

    @Autowired
    public UserDAOImpl(Map<Integer, User> userStorage) {
        this.userStorage = userStorage;
    }

    @Override
    public void save(User user) {
        userStorage.put(user.getId(), user);
        logger.info("User with ID {} has been saved", user.getId());
    }

    @Override
    public void update(User user) {
        userStorage.put(user.getId(), user);
        logger.info("User with ID {} has been updated", user.getId());
    }

    @Override
    public Optional<User> findById(int id) {
        var user = userStorage.get(id);
        if (user == null) {
            logger.info("User with ID {} has not been found", id);
            return Optional.empty();
        }
        logger.info("User with ID {} has been found", id);
        return Optional.of(user);
    }

    @Override
    public List<User> findAll() {
        var users = List.copyOf(userStorage.values());
        if (users.isEmpty()) {
            logger.info("No users have been found");
        } else {
            logger.info("All users have been found");
        }
        return users;
    }

    @Override
    public void delete(int id) {
        userStorage.remove(id);
        logger.info("User with ID {} has been deleted", id);
    }
}
