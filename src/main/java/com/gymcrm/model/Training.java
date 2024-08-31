package com.gymcrm.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Training {
    private Integer id;
    private Integer traineeId;
    private Integer trainerId;
    private String trainingName;
    private TrainingType trainingType;
    private LocalDateTime trainingDate;
    private long trainingDuration;
}
