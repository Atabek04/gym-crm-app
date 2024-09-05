package com.epam.gym.dao.impl;

import com.epam.gym.dao.UserDAO;
import com.epam.gym.dao.impl.prarameterResolver.UserDAOParameterResolver;
import com.epam.gym.exception.ResourceNotFoundException;
import com.epam.gym.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(UserDAOParameterResolver.class)
class UserDAOImplTest {

    @BeforeEach
    void setUp(UserDAO userDAO) {
        userDAO.findAll().forEach(user -> userDAO.delete(user.getId()));
    }

    @Test
    void shouldSaveUserSuccessfully(UserDAO userDAO) {
        User user = new User(1L, "John", "Doe", "John.Doe", "password123", true);
        userDAO.save(user, user.getId());

        var foundUser = userDAO.findById(1L).orElseThrow(() -> new ResourceNotFoundException("User not found"));

        assertAll(
                () -> assertNotNull(foundUser, "User should be found after save"),
                () -> assertEquals(1, foundUser.getId(), "User ID should match"),
                () -> assertEquals("John.Doe", foundUser.getUsername(), "Username should match")
        );
    }

    @Test
    void shouldUpdateUserPassword(UserDAO userDAO) {
        User user = new User(1L, "John", "Doe", "John.Doe", "password123", true);
        userDAO.save(user, user.getId());

        User updatedUser = new User(1L, "John", "Doe", "John.Doe", "password456", true);
        userDAO.update(updatedUser, updatedUser.getId());

        var foundUser = userDAO.findById(1L).orElseThrow(() -> new ResourceNotFoundException("User not found"));

        assertAll(
                () -> assertNotNull(foundUser, "User should be found after update"),
                () -> assertEquals(1, foundUser.getId(), "User ID should match"),
                () -> assertEquals("password456", foundUser.getPassword(), "Password should be updated")
        );
    }

    @Test
    void shouldDeleteUserSuccessfully(UserDAO userDAO) {
        User user = new User(1L, "John", "Doe", "John.Doe", "password123", true);
        userDAO.save(user, user.getId());
        userDAO.delete(1L);

        var foundUser = userDAO.findById(1L).orElse(null);

        assertNull(foundUser, "User should be deleted and not found");
    }
}