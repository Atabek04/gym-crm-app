package com.gymcrm.dto;

import com.gymcrm.model.TrainingType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class TrainingRequest {
    int id;
    int traineeId;
    int trainerId;
    String trainingName;
    TrainingType trainingType;
    LocalDateTime trainingDate;
    long trainingDuration;
}
