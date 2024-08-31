package com.gymcrm.mapper;

import com.gymcrm.dto.TrainingRequest;
import com.gymcrm.dto.TrainingResponse;
import com.gymcrm.model.Trainee;
import com.gymcrm.model.Trainer;
import com.gymcrm.model.Training;
import com.gymcrm.model.User;

public class TrainingMapper {
    public static Training convertToTraining(TrainingRequest trainingRequest) {
        Training training = new Training();
        training.setId(trainingRequest.getId());
        training.setTraineeId(trainingRequest.getTraineeId());
        training.setTrainerId(trainingRequest.getTrainerId());
        training.setTrainingDate(trainingRequest.getTrainingDate());
        training.setTrainingDuration(trainingRequest.getTrainingDuration());
        training.setTrainingType(trainingRequest.getTrainingType());
        return training;
    }

    public static TrainingResponse convertToTrainingResponse(Training training, User trainee, User trainer) {
        TrainingResponse response = new TrainingResponse();
        response.setId(training.getId());
        response.setTraineeId(training.getTraineeId());
        response.setTraineeFirstName(trainee.getFirstName());
        response.setTraineeLastName(trainee.getLastName());
        response.setTrainerId(training.getTrainerId());
        response.setTrainerFirstName(trainer.getFirstName());
        response.setTrainerLastName(trainer.getLastName());
        response.setTrainingName(training.getTrainingName());
        response.setTrainingType(training.getTrainingType());
        response.setTrainingDate(training.getTrainingDate());
        response.setTrainingDuration(training.getTrainingDuration());
        return response;
    }
}
