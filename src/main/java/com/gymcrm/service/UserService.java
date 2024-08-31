package com.gymcrm.service;

import com.gymcrm.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    void createUser(User user);

    void updateUser(User user);

    Optional<User> getUser(int id);

    List<User> getAllUsers();

    void deleteUser(int id);

}
