package com.epam.gym.service.impl;

import com.epam.gym.dao.UserDAO;
import com.epam.gym.model.User;
import com.epam.gym.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.epam.gym.util.UserUtils.generateRandomPassword;
import static com.epam.gym.util.UserUtils.generateUsername;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserDAO userDAO;

    @Override
    public void create(User user, Long id) {
        var username = generateUsername(
                user.getFirstName(),
                user.getLastName(),
                findAll().stream().map(User::getUsername).toList());
        user.setUsername(username);
        user.setPassword(generateRandomPassword());
        userDAO.save(user, id);
    }

    @Override
    public void update(User user, Long id) {
        var oldUser = findById(id).orElseThrow(() -> new IllegalArgumentException("User not found"));
        var username = "";
        if (user.getFirstName().equals(oldUser.getFirstName()) && user.getLastName().equals(oldUser.getLastName())) {
            username = oldUser.getUsername();
        } else {
            user.setUsername(oldUser.getUsername());
            username = generateUsername(
                    user.getFirstName(),
                    user.getLastName(),
                    findAll().stream().map(User::getUsername).toList());
        }
        user.setUsername(username);
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