package com.gymcrm.service;

import com.gymcrm.model.User;

public interface UserService extends BaseService<User> {
    void create(User user);
}
