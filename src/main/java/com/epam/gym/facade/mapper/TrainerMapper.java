package com.epam.gym.facade.mapper;

import com.epam.gym.dto.TrainerRequest;
import com.epam.gym.dto.TrainerResponse;
import com.epam.gym.model.Trainer;
import com.epam.gym.model.TrainingType;
import com.epam.gym.model.User;
import lombok.experimental.UtilityClass;

@UtilityClass
public class TrainerMapper {
    public static Trainer toTrainer(TrainerRequest trainerRequest, User user) {
        return Trainer.builder()
                .user(user)
                .trainingTypeId(TrainingType.valueOf(trainerRequest.specialization()).getId())
                .build();
    }

    public static TrainerResponse toTrainerResponse(Trainer trainer) {
        return TrainerResponse.builder()
                .trainerId(trainer.getId())
                .firstName(trainer.getUser().getFirstName())
                .lastName(trainer.getUser().getLastName())
                .username(trainer.getUser().getUsername())
                .isActive(trainer.getUser().isActive())
                .specialization(trainer.getTrainingType().toString())
                .build();
    }
}
