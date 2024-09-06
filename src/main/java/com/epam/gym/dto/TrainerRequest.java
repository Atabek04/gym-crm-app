package com.epam.gym.dto;

import lombok.Builder;

@Builder
public record TrainerRequest (
        String firstName,
        String lastName,
        String specialization
) {
}
