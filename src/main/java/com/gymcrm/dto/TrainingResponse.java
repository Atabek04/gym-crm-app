package com.gymcrm.dto;

import com.gymcrm.model.TrainingType;

import java.time.LocalDateTime;

public class TrainingResponse {
    private int id;
    private int traineeId;
    private String traineeFirstName;
    private String traineeLastName;
    private int trainerId;
    private String trainerFirstName;
    private String trainerLastName;
    private String trainingName;
    private TrainingType trainingType;
    private LocalDateTime trainingDate;
    private long trainingDuration;

    public TrainingResponse() {}

    public TrainingResponse(int id, int traineeId, String traineeFirstName, String traineeLastName, int trainerId, String trainerFirstName, String trainerLastName, String trainingName, TrainingType trainingType, LocalDateTime trainingDate, long trainingDuration) {
        this.id = id;
        this.traineeId = traineeId;
        this.traineeFirstName = traineeFirstName;
        this.traineeLastName = traineeLastName;
        this.trainerId = trainerId;
        this.trainerFirstName = trainerFirstName;
        this.trainerLastName = trainerLastName;
        this.trainingName = trainingName;
        this.trainingType = trainingType;
        this.trainingDate = trainingDate;
        this.trainingDuration = trainingDuration;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTraineeId(int traineeId) {
        this.traineeId = traineeId;
    }

    public void setTraineeFirstName(String traineeFirstName) {
        this.traineeFirstName = traineeFirstName;
    }

    public void setTraineeLastName(String traineeLastName) {
        this.traineeLastName = traineeLastName;
    }

    public void setTrainerId(int trainerId) {
        this.trainerId = trainerId;
    }

    public void setTrainerFirstName(String trainerFirstName) {
        this.trainerFirstName = trainerFirstName;
    }

    public void setTrainerLastName(String trainerLastName) {
        this.trainerLastName = trainerLastName;
    }

    public void setTrainingName(String trainingName) {
        this.trainingName = trainingName;
    }

    public void setTrainingType(TrainingType trainingType) {
        this.trainingType = trainingType;
    }

    public void setTrainingDate(LocalDateTime trainingDate) {
        this.trainingDate = trainingDate;
    }

    public void setTrainingDuration(long trainingDuration) {
        this.trainingDuration = trainingDuration;
    }
}
