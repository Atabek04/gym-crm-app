package com.epam.gym.dto;

import com.epam.gym.model.TrainingType;
import lombok.Builder;

import java.time.ZonedDateTime;

@Builder
public record TrainingRequest(
        Long traineeId,
        Long trainerId,
        String trainingName,
        TrainingType trainingType,
        ZonedDateTime trainingDate,
        Long trainingDuration
) {
}
