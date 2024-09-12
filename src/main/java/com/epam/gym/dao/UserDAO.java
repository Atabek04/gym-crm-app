package com.epam.gym.dao;

import com.epam.gym.model.User;

import java.util.Optional;

public interface UserDAO extends BaseDAO<User> {
    User findByUsernameAndPassword(String username, String password);

    void changeUserPassword(String username, String newPassword);

    void activateUser(String username);

    void deactivateUser(String username);

    Optional<User> findUserByUsername(String username);
}
