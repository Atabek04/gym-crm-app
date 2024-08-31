package com.gymcrm.dao;

import com.gymcrm.model.User;

import java.util.List;
import java.util.Optional;

public interface UserDAO {
    void save(User user);

    void update(User user);

    Optional<User> findById(int id);

    List<User> findAll();

    void delete(int id);
}
