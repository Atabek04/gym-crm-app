package com.epam.gym.service;

import com.epam.gym.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    void create(User user, Long id);

    void update(User user, Long id);

    Optional<User> findById(Long id);

    List<User> findAll();

    void delete(Long id);
}
