package com.epam.gym.security;

import lombok.experimental.UtilityClass;

@UtilityClass
public class AuthenticationContext {
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