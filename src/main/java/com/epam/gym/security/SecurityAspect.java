package com.epam.gym.security;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class SecurityAspect {

    @Before("@annotation(secured)")
    public void checkSecurity(Secured secured) {
        AuthenticatedUser authenticatedUser = AuthenticationContext.getAuthenticatedUser();

        if (authenticatedUser == null) {
            throw new SecurityException("No authenticated user found");
        }

        UserRole[] allowedRoles = secured.value();
        boolean authorized = Arrays.stream(allowedRoles)
                .anyMatch(role -> role.equals(authenticatedUser.getRole()));

        if (!authorized) {
            throw new SecurityException("User does not have the required authority to access this method");
        }
    }
}