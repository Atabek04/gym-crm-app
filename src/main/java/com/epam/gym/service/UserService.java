package com.epam.gym.service;

import com.epam.gym.model.User;

public interface UserService extends BaseService<User> {
    void create(User user);
}
