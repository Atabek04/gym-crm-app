package com.epam.gym.service.impl;

import com.epam.gym.dao.UserDAO;
import com.epam.gym.model.User;
import com.epam.gym.service.AbstractService;
import com.epam.gym.service.UserService;
import com.epam.gym.util.UserUtils;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends AbstractService<User> implements UserService {
    public UserServiceImpl(UserDAO userDAO) {
        super(userDAO);
    }

    @Override
    public void create(User user) {
        var username = UserUtils.generateUsername(user.getFirstName(), user.getLastName(),
                super.findAll().stream().map(User::getUsername).toList());
        var password = UserUtils.generateRandomPassword();
        user.setUsername(username);
        user.setPassword(password);
        System.out.println("Username: " + username);
        dao.save(user, user.getId());
    }
}