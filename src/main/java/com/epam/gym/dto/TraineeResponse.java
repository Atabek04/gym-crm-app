package com.epam.gym.dto;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record TraineeResponse(
        Long traineeID,
        String firstName,
        String lastName,
        String username,
        boolean isActive,
        LocalDate dateOfBirth,
        String address
) {
}