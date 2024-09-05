package com.epam.gym.dto;

import com.epam.gym.model.TrainingType;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record TrainingResponse (
        Long id,
        Long traineeId,
        Long trainerId,
        String traineeFirstName,
        String traineeLastName,
        String trainerFirstName,
        String trainerLastName,
        String trainingName,
        TrainingType trainingType,
        LocalDateTime trainingDate,
        Long trainingDuration
) {
}
