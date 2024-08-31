package com.gymcrm.service.impl;

import com.gymcrm.dao.UserDAO;
import com.gymcrm.exception.ResourceNotFoundException;
import com.gymcrm.model.User;
import com.gymcrm.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserDAO userDAO;

    public UserServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public void createUser(User user) {
        userDAO.save(user);
        logger.info("User with ID {} created successfully.", user.getId());
    }

    @Override
    public void updateUser(User user) {
        if (userDAO.findById(user.getId()).isEmpty()) {
            throw new ResourceNotFoundException("User with ID " + user.getId() + " not found.");
        }
        userDAO.update(user);
        logger.info("User with ID {} updated successfully.", user.getId());
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
        if (userDAO.findById(id).isEmpty()) {
            throw new ResourceNotFoundException("User with ID " + id + " not found.");
        }
        userDAO.delete(id);
        logger.info("User with ID {} deleted successfully.", id);
    }
}