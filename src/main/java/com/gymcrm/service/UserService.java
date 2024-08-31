package com.gymcrm.service;

import com.gymcrm.model.User;

import java.util.List;

public interface UserService {
    void createUser(User user);

    void updateUser(User user);

    User getUser(int id);

    List<User> getAllUsers();

    void deleteUser(int id);

}
