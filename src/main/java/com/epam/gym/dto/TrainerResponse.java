package com.epam.gym.dto;

import lombok.Builder;

@Builder
public record TrainerResponse(
        Long trainerId,
        String firstName,
        String lastName,
        String username,
        boolean isActive,
        String specialization
) {
}
