package com.epam.gym.controller;

import com.epam.gym.dto.UserCredentials;
import com.epam.gym.dto.UserNewPasswordCredentials;
import com.epam.gym.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/auth")
public class AuthController {
    private final UserService userService;

    @GetMapping("/login")
    public ResponseEntity<String> login(
            @Valid @RequestBody UserCredentials credentials
    ) {
        var user = userService.findByUsernameAndPassword(credentials.username(), credentials.password());
        if (user.isPresent()) {
            return ResponseEntity.ok("Successful Login!");
        } else {
            return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).body("Wrong password or username");
        }
    }

    @PutMapping("/password")
    public ResponseEntity<String> changePassword(
            @Valid @RequestBody UserNewPasswordCredentials credentials
    ) {
        userService.validateAndChangePassword(credentials);
        return ResponseEntity.ok("User's new password is updated successfully");
    }

}
