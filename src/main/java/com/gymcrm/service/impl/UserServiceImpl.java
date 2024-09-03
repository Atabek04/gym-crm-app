package com.gymcrm.service.impl;

import com.gymcrm.dao.UserDAO;
import com.gymcrm.model.User;
import com.gymcrm.service.AbstractService;
import com.gymcrm.service.UserService;
import com.gymcrm.util.UserUtils;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends AbstractService<User> implements UserService {
    public UserServiceImpl(UserDAO userDAO) {
        super(userDAO);
    }

    @Override
    public void create(User user) {
        if (dao.findById(user.getId()).isPresent()) {
            dao.update(user, user.getId());
        } else {
            var username = UserUtils.generateUsername(user.getFirstName(), user.getLastName(),
                    super.findAll().stream().map(User::getUsername).toList());
            var password = UserUtils.generateRandomPassword();
            user.setUsername(username);
            user.setPassword(password);
            dao.save(user, user.getId());
        }
    }
}