package com.epam.gym.mapper;

import com.epam.gym.dto.TrainingRequest;
import com.epam.gym.dto.TrainingResponse;
import com.epam.gym.model.Training;
import com.epam.gym.model.User;
import lombok.experimental.UtilityClass;

@UtilityClass
public class TrainingMapper {
    public static Training toTraining(TrainingRequest trainingRequest) {
        return Training.builder()
                .id(trainingRequest.id())
                .traineeId(trainingRequest.traineeId())
                .trainerId(trainingRequest.trainerId())
                .trainingName(trainingRequest.trainingName())
                .trainingType(trainingRequest.trainingType())
                .trainingDate(trainingRequest.trainingDate())
                .trainingDuration(trainingRequest.trainingDuration())
                .build();
    }

    public static TrainingResponse toTrainingResponse(Training training, User trainee, User trainer) {
        return TrainingResponse.builder()
                .id(training.getId())
                .traineeId(training.getTraineeId())
                .traineeFirstName(trainee.getFirstName())
                .traineeLastName(trainee.getLastName())
                .trainerId(training.getTrainerId())
                .trainerFirstName(trainer.getFirstName())
                .trainerLastName(trainer.getLastName())
                .trainingName(training.getTrainingName())
                .trainingType(training.getTrainingType())
                .trainingDate(training.getTrainingDate())
                .trainingDuration(training.getTrainingDuration())
                .build();
    }
}
