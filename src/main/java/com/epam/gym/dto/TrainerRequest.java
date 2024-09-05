package com.epam.gym.dto;

import lombok.Builder;

@Builder
public record TrainerRequest (
        Long trainerId,
        Long userId,
        String firstName,
        String lastName,
        String specialization
) {
}
