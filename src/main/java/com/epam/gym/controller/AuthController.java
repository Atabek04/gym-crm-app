package com.epam.gym.controller;

import com.epam.gym.dto.UserCredentials;
import com.epam.gym.dto.UserNewPasswordCredentials;
import com.epam.gym.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/auth")
@Slf4j
public class AuthController {
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody UserCredentials credentials) {
        log.info("Login attempt for user: {}", credentials.username());
        var user = userService.findByUsernameAndPassword(credentials.username(), credentials.password());
        if (user.isPresent()) {
            log.info("Successful login for user: {}", credentials.username());
            return ResponseEntity.ok("Successful Login!");
        } else {
            log.warn("Failed login attempt for user: {}", credentials.username());
            return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).body("Wrong password or username");
        }
    }

    @PutMapping("/password")
    public ResponseEntity<String> changePassword(@Valid @RequestBody UserNewPasswordCredentials credentials) {
        log.info("Password change requested for user: {}", credentials.username());
        userService.validateAndChangePassword(credentials);
        log.info("Password successfully changed for user: {}", credentials.username());
        return ResponseEntity.ok("User's new password is updated successfully");
    }
}
