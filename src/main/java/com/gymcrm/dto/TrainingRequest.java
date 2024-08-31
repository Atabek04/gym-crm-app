package com.gymcrm.dto;

import com.gymcrm.model.TrainingType;
import jakarta.validation.constraints.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class TrainingRequest {
    @NotNull
    @Min(value = 1, message = "Trainer ID must be greater than 0")
    int id;

    @NotNull
    @Min(value = 1, message = "Trainee ID must be greater than 0")
    int traineeId;

    @NotNull
    @Min(value = 1, message = "Trainer ID must be greater than 0")
    int trainerId;

    @NotBlank
    @Size(max = 100, message = "Training name must not exceed 100 characters")
    String trainingName;

    @NotNull
    TrainingType trainingType;

    @NotNull
    @FutureOrPresent(message = "Training date must be in the present or future")
    LocalDateTime trainingDate;

    @Min(value = 1, message = "Training duration must be greater than 0")
    long trainingDuration;
}
