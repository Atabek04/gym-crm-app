package com.epam.gym.service.impl;

import com.epam.gym.dao.UserDAO;
import com.epam.gym.model.User;
import com.epam.gym.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserDAO userDAO;

    @Override
    public void create(User user, Long id) {
        userDAO.save(user, id);
    }

    @Override
    public void update(User user, Long id) {
        userDAO.update(user, id);
    }

    @Override
    public Optional<User> findById(Long id) {
        return userDAO.findById(id);
    }

    @Override
    public List<User> findAll() {
        return userDAO.findAll();
    }

    @Override
    public void delete(Long id) {
        userDAO.delete(id);
    }
}