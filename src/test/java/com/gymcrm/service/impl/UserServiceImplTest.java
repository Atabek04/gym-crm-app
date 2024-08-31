package com.gymcrm.service.impl;

import com.gymcrm.model.User;
import com.gymcrm.service.UserService;
import com.gymcrm.service.impl.parameterResolver.UserServiceParameterResolver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(UserServiceParameterResolver.class)
class UserServiceImplTest {

    @BeforeEach
    void setUp(UserService userService) {
        userService.getAllUsers().forEach(user -> userService.deleteUser(user.getId()));
    }

    @Test
    void shouldCreateUserSuccessfully(UserService userService) {
        User user = new User(1, "John", "Doe", "john.doe", 
                "password123", true);

        userService.createUser(user);

        User foundUser = userService.getUser(1).orElse(null);

        assertNotNull(foundUser, "User should be found");
        assertEquals(1, foundUser.getId(), "User ID should be 1");
        assertEquals("john.doe", foundUser.getUsername(), "Username should be 'john.doe'");
    }

    @Test
    void shouldUpdateUserPassword(UserService userService) {
        User originalUser = new User(1, "John", "Doe", "john.doe", 
                "password123", true);
        userService.createUser(originalUser);

        User updatedUser = new User(1, "John", "Doe", "john.doe", 
                "newPassword456", true);
        userService.updateUser(updatedUser);

        User foundUser = userService.getUser(1).orElse(null);

        assertNotNull(foundUser, "User should be found");
        assertEquals("newPassword456", foundUser.getPassword(), 
                "Password should be updated to 'newPassword456'");
    }

    @Test
    void shouldReturnAllUsers(UserService userService) {
        User user1 = new User(1, "John", "Doe", "john.doe", 
                "password123", true);
        User user2 = new User(2, "Jane", "Smith", "jane.smith", 
                "password456", false);

        userService.createUser(user1);
        userService.createUser(user2);

        List<User> allUsers = userService.getAllUsers();

        assertEquals(2, allUsers.size(), "There should be 2 users");
        assertTrue(allUsers.contains(user1), "User 1 should be in the list");
        assertTrue(allUsers.contains(user2), "User 2 should be in the list");
    }

    @Test
    void shouldDeleteUserSuccessfully(UserService userService) {
        User user = new User(1, "John", "Doe", "john.doe",
                "password123", true);
        userService.createUser(user);

        userService.deleteUser(1);

        var foundUser = userService.getUser(1).orElse(null);

        assertNull(foundUser, "User should be deleted and not found");
    }
}