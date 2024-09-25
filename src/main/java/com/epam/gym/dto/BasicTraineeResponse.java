package com.epam.gym.dto;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record BasicTraineeResponse(
        String username,
        String firstName,
        String lastName,
        LocalDate dateOfBirth,
        String address,
        boolean isActive
) {
}
