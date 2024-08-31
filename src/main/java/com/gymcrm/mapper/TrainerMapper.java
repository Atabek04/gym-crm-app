package com.gymcrm.mapper;

import com.gymcrm.dto.TrainerRequest;
import com.gymcrm.dto.TrainerResponse;
import com.gymcrm.model.Trainer;
import com.gymcrm.model.User;

public class TrainerMapper {
    public static Trainer convertToTrainer(TrainerRequest trainerRequest) {
        Trainer trainer = new Trainer();
        trainer.setId(trainerRequest.getId());
        trainer.setUserId(trainerRequest.getId());
        trainer.setSpecialization(trainerRequest.getSpecialization());
        return trainer;
    }

    public static TrainerResponse convertToTrainerResponse(Trainer trainer, User user) {
        TrainerResponse response = new TrainerResponse();
        response.setId(trainer.getId());
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        response.setUsername(user.getUsername());
        response.setActive(user.isActive());
        response.setSpecialization(trainer.getSpecialization().toString());
        return response;
    }
}
