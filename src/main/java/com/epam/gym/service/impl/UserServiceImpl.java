package com.epam.gym.service.impl;

import com.epam.gym.dao.UserDAO;
import com.epam.gym.exception.ResourceNotFoundException;
import com.epam.gym.model.User;
import com.epam.gym.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.epam.gym.util.UserUtils.generateRandomPassword;
import static com.epam.gym.util.UserUtils.generateUsername;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserDAO userDAO;

    @Override
    public Optional<User> create(User user) {
        Optional<User> userOptional;
        log.info("UserService:: Saving the user");
        if (user.getId() != null) {
            userOptional = userDAO.update(user, user.getId());
        } else {
            var username = generateUsername(
                    user.getFirstName(),
                    user.getLastName(),
                    findAll().stream().map(User::getUsername).toList());
            user.setUsername(username);
            user.setPassword(generateRandomPassword());
            userOptional = userDAO.save(user);
        }
        return userOptional;
    }

    @Override
    public Optional<User> update(User user, Long id) {
        var oldUser = userDAO.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found with id: "+id));
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
        user.setPassword(oldUser.getPassword());
        return userDAO.update(user, id);
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

    @Override
    public Optional<User> findByUsernameAndPassword(String username, String password) {
        return Optional.ofNullable(userDAO.findByUsernameAndPassword(username, password));
    }

    @Override
    public void changeUserPassword(String username, String newPassword) {
        userDAO.changeUserPassword(username, newPassword);
    }

    @Override
    public void activateUser(String username) {
        userDAO.activateUser(username);
    }

    @Override
    public void deactivateUser(String username) {
        userDAO.deactivateUser(username);
    }

    @Override
    public Optional<User> findUserByUsername(String username) {
        return userDAO.findUserByUsername(username);
    }
}