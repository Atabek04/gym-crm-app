package com.gymcrm.mapper;

import com.gymcrm.dto.TrainingRequest;
import com.gymcrm.dto.TrainingResponse;
import com.gymcrm.model.Training;
import com.gymcrm.model.User;
import org.springframework.stereotype.Component;

@Component
public final class TrainingMapper {
    public static Training toTraining(TrainingRequest trainingRequest) {
        return Training.builder()
                .id(trainingRequest.getId())
                .traineeId(trainingRequest.getTraineeId())
                .trainerId(trainingRequest.getTrainerId())
                .trainingName(trainingRequest.getTrainingName())
                .trainingType(trainingRequest.getTrainingType())
                .trainingDate(trainingRequest.getTrainingDate())
                .trainingDuration(trainingRequest.getTrainingDuration())
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
