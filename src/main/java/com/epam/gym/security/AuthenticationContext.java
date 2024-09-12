package com.epam.gym.security;

import org.springframework.stereotype.Component;

@Component
public class AuthenticationContext {
    private AuthenticationContext() {
        throw new IllegalArgumentException("Can't instantiate AuthenticationContext");
    }
    private static final ThreadLocal<AuthenticatedUser> userHolder = new ThreadLocal<>();

    public static void setAuthenticatedUser(AuthenticatedUser user) {
        userHolder.set(user);
    }

    public static AuthenticatedUser getAuthenticatedUser() {
        return userHolder.get();
    }

    public static void clear() {
        userHolder.remove();
    }
}