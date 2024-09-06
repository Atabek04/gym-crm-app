package com.epam.gym.dto;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record TraineeRequest(
        String address,
        String firstName,
        String lastName,
        LocalDate dateOfBirth
) {
}