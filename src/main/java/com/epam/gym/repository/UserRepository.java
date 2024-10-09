package com.epam.gym.repository;

import com.epam.gym.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    User findByUsernameAndPassword(String username, String password);

    @Modifying
    @Query("UPDATE User u SET u.password = :newPassword WHERE u.username = :username")
    void changePassword(String username, String newPassword);

    @Modifying
    @Query("UPDATE User u SET u.isActive = true WHERE u.username = :username AND u.isActive = false")
    void activateUser(String username);

    @Modifying
    @Query("UPDATE User u SET u.isActive = false WHERE u.username = :username AND u.isActive = true")
    void deactivateUser(String username);

    @Query("SELECT u.username FROM User u")
    List<String> findAllUsernames();
}
