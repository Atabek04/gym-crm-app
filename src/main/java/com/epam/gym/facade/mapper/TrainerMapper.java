package com.epam.gym.facade.mapper;

import com.epam.gym.dto.TrainerRequest;
import com.epam.gym.dto.TrainerResponse;
import com.epam.gym.model.Trainer;
import com.epam.gym.model.TrainingType;
import com.epam.gym.model.User;
import lombok.experimental.UtilityClass;

@UtilityClass
public class TrainerMapper {
    public static Trainer toTrainer(TrainerRequest trainerRequest) {
        return Trainer.builder()
                .specialization(TrainingType.valueOf(trainerRequest.specialization()))
                .build();
    }

    public static TrainerResponse toTrainerResponse(Trainer trainer, User user) {
        return TrainerResponse.builder()
                .trainerId(trainer.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .username(user.getUsername())
                .isActive(user.isActive())
                .specialization(trainer.getSpecialization().toString())
                .build();
    }
}
