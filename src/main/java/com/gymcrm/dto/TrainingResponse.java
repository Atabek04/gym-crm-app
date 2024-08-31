package com.gymcrm.dto;

import com.gymcrm.model.TrainingType;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@Builder
public class TrainingResponse {
    int id;
    int traineeId;
    String traineeFirstName;
    String traineeLastName;
    int trainerId;
    String trainerFirstName;
    String trainerLastName;
    String trainingName;
    TrainingType trainingType;
    LocalDateTime trainingDate;
    long trainingDuration;

    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", Trainee = {traineeId=" + traineeId +
                ", traineeFirstName='" + traineeFirstName + '\'' +
                ", traineeLastName='" + traineeLastName + '\'' +
                "}, Trainer = {trainerId=" + trainerId +
                ", trainerFirstName='" + trainerFirstName + '\'' +
                ", trainerLastName='" + trainerLastName + '\'' +
                "}, trainingName='" + trainingName + '\'' +
                ", trainingType=" + trainingType +
                ", trainingDate=" + trainingDate +
                ", trainingDuration=" + trainingDuration +
                '}';
    }
}
