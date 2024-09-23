package com.epam.gym.service.impl;

import com.epam.gym.exception.AuthenticationException;
import com.epam.gym.security.AuthenticatedUser;
import com.epam.gym.security.AuthenticationContext;
import com.epam.gym.service.AuthenticationService;
import com.epam.gym.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Base64;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserService userService;

    @Override
    public void authenticate(String authenticationHeader) {
        if (authenticationHeader != null && authenticationHeader.startsWith("Basic ")) {
            var base64Credentials = authenticationHeader.substring(6);
            var credentials = new String(Base64.getDecoder().decode(base64Credentials));
            var values = credentials.split(":", 2);
            String username = values[0];
            String password = values[1];

            var user = userService.findByUsernameAndPassword(username, password);

            if (user.isPresent()) {
                var loggedUser = user.get();
                var authenticatedUser = AuthenticatedUser.builder()
                        .id(loggedUser.getId())
                        .username(loggedUser.getUsername())
                        .role(loggedUser.getRole())
                        .build();
                AuthenticationContext.setAuthenticatedUser(authenticatedUser);
            } else {
                throw new AuthenticationException("Invalid credentials. Wrong username or password");
            }
        } else {
            throw new AuthenticationException("Missing or invalid Authorization header");
        }
    }
}
