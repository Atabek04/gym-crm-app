package com.gymcrm.mapper;

import com.gymcrm.dto.TrainerRequest;
import com.gymcrm.dto.TrainerResponse;
import com.gymcrm.model.Trainer;
import com.gymcrm.model.TrainingType;
import com.gymcrm.model.User;
import org.springframework.stereotype.Component;

@Component
public class TrainerMapper {
    public static Trainer toTrainer(TrainerRequest trainerRequest) {
        return Trainer.builder()
                .id(trainerRequest.getTrainerId())
                .userId(trainerRequest.getUserId())
                .specialization(TrainingType.valueOf(trainerRequest.getSpecialization()))
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
