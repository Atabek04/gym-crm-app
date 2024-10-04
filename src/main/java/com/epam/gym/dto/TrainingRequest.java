package com.epam.gym.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.time.ZonedDateTime;

@Builder
public record TrainingRequest(
        @NotBlank(message = "Trainee username is required")
        String traineeUsername,
        @NotBlank(message = "Trainer username is required")
        String trainerUsername,

        @NotBlank(message = "Training name is required")
        String trainingName,

        @NotNull(message = "Training date is required")
        ZonedDateTime trainingDate,

        @NotNull(message = "Training duration is required")
        Long trainingDuration
) {
}
