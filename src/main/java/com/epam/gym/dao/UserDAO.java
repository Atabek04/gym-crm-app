package com.epam.gym.dao;

import com.epam.gym.model.User;

public interface UserDAO extends BaseDAO<User> {
    User findByUsernameAndPassword(String username, String password);

    void changeUserPassword(String username, String newPassword);
}
