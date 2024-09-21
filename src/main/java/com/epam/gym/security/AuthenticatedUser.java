package com.epam.gym.security;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AuthenticatedUser {
    private Long id;
    private String username;
    private UserRole role;
}
