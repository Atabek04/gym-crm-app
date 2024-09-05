package com.epam.gym.service.impl;

import com.epam.gym.model.Trainer;
import com.epam.gym.model.TrainingType;
import com.epam.gym.service.TrainerService;
import com.epam.gym.service.impl.parameterResolver.TrainerServiceParameterResolver;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(TrainerServiceParameterResolver.class)
class TrainerServiceImplTest {
    @Test
    void shouldCreateTrainerSuccessfully(TrainerService trainerService) {
        Trainer trainer = new Trainer(3L, 14L, TrainingType.STRENGTH_TRAINING);
        trainerService.create(trainer, trainer.getId());

        Trainer foundTrainer = trainerService.findById(3L)
                .orElseThrow(() -> new RuntimeException("Trainer not found"));

        assertAll(
                () -> assertNotNull(foundTrainer, "Trainer should be found"),
                () -> assertEquals(3L, foundTrainer.getId(), "Trainer id should be 3"),
                () -> assertEquals(14L, foundTrainer.getUserId(), "Trainer id should be 14"),
                () -> assertEquals(TrainingType.STRENGTH_TRAINING, foundTrainer.getSpecialization(),
                        "Specialization should be 'STRENGTH_TRAINING'")
        );
    }

    @Test
    void shouldUpdateTrainerSpecialization(TrainerService trainerService) {
        Trainer originalTrainer = new Trainer(3L, 14L, TrainingType.STRENGTH_TRAINING);
        trainerService.create(originalTrainer, originalTrainer.getId());

        Trainer updatedTrainer = new Trainer(3L, 14L, TrainingType.CARDIO_TRAINING);
        trainerService.update(updatedTrainer, updatedTrainer.getId());

        Trainer foundTrainer = trainerService.findById(3L)
                .orElseThrow(() -> new RuntimeException("Trainer not found"));

        assertAll(
                () -> assertNotNull(foundTrainer, "Trainer should be found"),
                () -> assertEquals(3L, foundTrainer.getId(), "Trainer id should be 3"),
                () -> assertEquals(14L, foundTrainer.getUserId(), "Trainer id should be 14"),
                () -> assertEquals(TrainingType.CARDIO_TRAINING, foundTrainer.getSpecialization(),
                        "Specialization should be 'CARDIO_TRAINING'")
        );
    }

    @Test
    void shouldReturnAllTrainers(TrainerService trainerService) {
        Trainer trainer1 = new Trainer(3L, 14L, TrainingType.STRENGTH_TRAINING);
        Trainer trainer2 = new Trainer(4L, 15L, TrainingType.CARDIO_TRAINING);

        trainerService.create(trainer1, trainer1.getId());
        trainerService.create(trainer2, trainer2.getId());

        List<Trainer> allTrainers = trainerService.findAll();

        assertAll(
                () -> assertEquals(2, allTrainers.size(), "There should be 2 trainers"),
                () -> assertTrue(allTrainers.contains(trainer1), "Trainer 1 should be in the list"),
                () -> assertTrue(allTrainers.contains(trainer2), "Trainer 2 should be in the list")
        );
    }
}