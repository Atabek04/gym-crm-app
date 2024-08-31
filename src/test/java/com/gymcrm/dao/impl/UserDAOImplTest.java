package com.gymcrm.dao.impl;

import com.gymcrm.dao.UserDAO;
import com.gymcrm.dao.impl.prarameterResolver.UserDAOParameterResolver;
import com.gymcrm.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(UserDAOParameterResolver.class)
class UserDAOImplTest {

    @BeforeEach
    void setUp(UserDAO userDAO) {
        userDAO.findAll().forEach(user -> userDAO.delete(user.getId()));
    }

    @Test
    void shouldSaveUserSuccessfully(UserDAO userDAO) {
        User user = new User(1, "John", "Doe", "John.Doe", "password123", true);
        userDAO.save(user);

        var foundUser = userDAO.findById(1);

        assertTrue(foundUser.isPresent(), "User should be found after save");
        assertEquals(1, foundUser.get().getId(), "User ID should match");
        assertEquals("John.Doe", foundUser.get().getUsername(), "Username should match");
    }

    @Test
    void shouldUpdateUserPassword(UserDAO userDAO) {
        User user = new User(1, "John", "Doe", "John.Doe", "password123", true);
        userDAO.save(user);

        User updatedUser = new User(1, "John", "Doe", "John.Doe", "password456", true);
        userDAO.update(updatedUser);

        var foundUser = userDAO.findById(1);

        assertTrue(foundUser.isPresent(), "User should be found after update");
        assertEquals("password456", foundUser.get().getPassword(), "Password should be updated");
    }

    @Test
    void shouldDeleteUserSuccessfully(UserDAO userDAO) {
        User user = new User(1, "John", "Doe", "John.Doe", "password123", true);
        userDAO.save(user);
        userDAO.delete(1);

        var foundUser = userDAO.findById(1);

        assertTrue(foundUser.isEmpty(), "User should be deleted and not found");
    }
}