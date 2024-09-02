package com.gymcrm.service.impl;

import com.gymcrm.model.Trainer;
import com.gymcrm.model.TrainingType;
import com.gymcrm.service.TrainerService;
import com.gymcrm.service.impl.parameterResolver.TrainerServiceParameterResolver;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(TrainerServiceParameterResolver.class)
class TrainerServiceImplTest {
    @Test
    void shouldCreateTrainerSuccessfully(TrainerService trainerService) {
        Trainer trainer = new Trainer(3, 14, TrainingType.STRENGTH_TRAINING);
        trainerService.create(trainer, trainer.getId());

        Trainer foundTrainer = trainerService.findById(3)
                .orElseThrow(() -> new RuntimeException("Trainer not found"));

        assertNotNull(foundTrainer, "Trainer should be found");
        assertEquals(3, foundTrainer.getId(), "Trainer ID should be 3");
    }

    @Test
    void shouldUpdateTrainerSpecialization(TrainerService trainerService) {
        Trainer originalTrainer = new Trainer(3, 14, TrainingType.STRENGTH_TRAINING);
        trainerService.create(originalTrainer, originalTrainer.getId());

        Trainer updatedTrainer = new Trainer(3, 14, TrainingType.CARDIO_TRAINING);
        trainerService.update(updatedTrainer, updatedTrainer.getId());

        Trainer foundTrainer = trainerService.findById(3)
                .orElseThrow(() -> new RuntimeException("Trainer not found"));

        assertNotNull(foundTrainer, "Trainer should be found");
        assertEquals(TrainingType.CARDIO_TRAINING, foundTrainer.getSpecialization(),
                "Specialization should be 'CARDIO_TRAINING'");
    }

    @Test
    void shouldReturnAllTrainers(TrainerService trainerService) {
        Trainer trainer1 = new Trainer(3, 14, TrainingType.STRENGTH_TRAINING);
        Trainer trainer2 = new Trainer(4, 15, TrainingType.CARDIO_TRAINING);

        trainerService.create(trainer1, trainer1.getId());
        trainerService.create(trainer2, trainer2.getId());

        List<Trainer> allTrainers = trainerService.findAll();

        assertEquals(2, allTrainers.size(), "There should be 2 trainers");
        assertTrue(allTrainers.contains(trainer1), "Trainer 1 should be in the list");
        assertTrue(allTrainers.contains(trainer2), "Trainer 2 should be in the list");
    }
}