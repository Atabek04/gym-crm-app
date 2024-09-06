package com.epam.gym.dto;

import com.epam.gym.model.TrainingType;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record TrainingRequest(
        Long traineeId,
        Long trainerId,
        String trainingName,
        TrainingType trainingType,
        LocalDateTime trainingDate,
        Long trainingDuration
) {
}
