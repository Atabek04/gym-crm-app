package com.epam.gym.service.impl;

import com.epam.gym.dao.UserDAO;
import com.epam.gym.dto.UserNewPasswordCredentials;
import com.epam.gym.exception.AuthenticationException;
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
        log.info("Creating user with firstName: {} and lastName: {}", user.getFirstName(), user.getLastName());
        Optional<User> userOptional;
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
        log.info("User created with username: {}", user.getUsername());
        return userOptional;
    }

    @Override
    public Optional<User> update(User user, Long id) {
        log.info("Updating user with ID: {}", id);
        var oldUser = userDAO.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        user.setUsername(oldUser.getUsername());
        user.setPassword(oldUser.getPassword());
        return userDAO.update(user, id);
    }

    @Override
    public Optional<User> findById(Long id) {
        log.info("Fetching user by ID: {}", id);
        return userDAO.findById(id);
    }

    @Override
    public List<User> findAll() {
        log.info("Fetching all users.");
        return userDAO.findAll();
    }

    @Override
    public void delete(Long id) {
        log.info("Deleting user with ID: {}", id);
        userDAO.delete(id);
        log.info("User with ID: {} deleted successfully.", id);
    }

    @Override
    public Optional<User> findByUsernameAndPassword(String username, String password) {
        log.info("Authenticating user with username: {}", username);
        return Optional.ofNullable(userDAO.findByUsername(username, password));
    }

    @Override
    public void changePassword(String username, String newPassword) {
        log.info("Changing password for user with username: {}", username);
        userDAO.changePassword(username, newPassword);
        log.info("Password changed successfully for username: {}", username);
    }

    @Override
    public void activateUser(String username) {
        log.info("Activating user with username: {}", username);
        userDAO.activateUser(username);
        log.info("User with username: {} activated.", username);
    }

    @Override
    public void deactivateUser(String username) {
        log.info("Deactivating user with username: {}", username);
        userDAO.deactivateUser(username);
        log.info("User with username: {} deactivated.", username);
    }

    @Override
    public Optional<User> findUserByUsername(String username) {
        log.info("Fetching user by username: {}", username);
        return userDAO.findUserByUsername(username);
    }

    @Override
    public void validateAndChangePassword(UserNewPasswordCredentials credentials) {
        log.info("Validating old password and changing password for user with username: {}", credentials.username());
        var user = findUserByUsername(credentials.username())
                .orElseThrow(() -> new ResourceNotFoundException("User with this username not found"));
        if (user.getPassword().equals(credentials.oldPassword())) {
            changePassword(credentials.username(), credentials.newPassword());
        } else {
            log.error("Old password mismatch for user: {}", credentials.username());
            throw new AuthenticationException("Failed to auth. Wrong old password");
        }
        log.info("Password changed successfully for user: {}", credentials.username());
    }
}
