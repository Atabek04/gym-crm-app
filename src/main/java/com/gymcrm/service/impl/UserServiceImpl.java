package com.gymcrm.service.impl;

import com.gymcrm.dao.UserDAO;
import com.gymcrm.model.User;
import com.gymcrm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserDAO userDAO;

    @Autowired
    public UserServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public void createUser(User user) {
        userDAO.save(user);
    }

    @Override
    public void updateUser(User user) {
        userDAO.update(user);
    }

    @Override
    public Optional<User> getUser(int id) {
        return userDAO.findById(id);
    }

    @Override
    public List<User> getAllUsers() {
        return userDAO.findAll();
    }

    @Override
    public void deleteUser(int id) {
        userDAO.delete(id);
    }
}
