package com.epam.gym.dto;

public record NewPasswordRequest(
        String username,
        String oldPassword,
        String newPassword
) {
}
