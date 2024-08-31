package com.gymcrm.dto;

import com.gymcrm.model.TrainingType;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;

public class TrainingRequest {
    @NotNull(message = "Training ID is required")
    @Min(value = 1, message = "Trainer ID must be greater than 0")
    private int id;

    @NotNull(message = "Trainee ID is required")
    @Min(value = 1, message = "Trainee ID must be greater than 0")
    private int traineeId;

    @NotNull(message = "Trainer ID is required")
    @Min(value = 1, message = "Trainer ID must be greater than 0")
    private int trainerId;

    @NotBlank(message = "Training name is required")
    @Size(max = 100, message = "Training name must not exceed 100 characters")
    private String trainingName;

    @NotNull(message = "Training type is required")
    private TrainingType trainingType;

    @NotNull(message = "Training date is required")
    @FutureOrPresent(message = "Training date must be in the present or future")
    private LocalDateTime trainingDate;

    @Min(value = 1, message = "Training duration must be greater than 0")
    private long trainingDuration;

    public int getId() {
        return id;
    }

    public int getTraineeId() {
        return traineeId;
    }

    public int getTrainerId() {
        return trainerId;
    }

    public String getTrainingName() {
        return trainingName;
    }

    public TrainingType getTrainingType() {
        return trainingType;
    }

    public LocalDateTime getTrainingDate() {
        return trainingDate;
    }

    public long getTrainingDuration() {
        return trainingDuration;
    }
}
