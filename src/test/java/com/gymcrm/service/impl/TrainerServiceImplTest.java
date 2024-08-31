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
        trainerService.createTrainer(trainer);

        Trainer foundTrainer = trainerService.getTrainer(3)
                .orElseThrow(() -> new RuntimeException("Trainer not found"));

        assertNotNull(foundTrainer, "Trainer should be found");
        assertEquals(3, foundTrainer.getId(), "Trainer ID should be 3");
    }

    @Test
    void shouldUpdateTrainerSpecialization(TrainerService trainerService) {
        Trainer originalTrainer = new Trainer(3, 14, TrainingType.STRENGTH_TRAINING);
        trainerService.createTrainer(originalTrainer);

        Trainer updatedTrainer = new Trainer(3, 14, TrainingType.CARDIO_TRAINING);
        trainerService.updateTrainer(updatedTrainer);

        Trainer foundTrainer = trainerService.getTrainer(3)
                .orElseThrow(() -> new RuntimeException("Trainer not found"));

        assertNotNull(foundTrainer, "Trainer should be found");
        assertEquals(TrainingType.CARDIO_TRAINING, foundTrainer.getSpecialization(),
                "Specialization should be 'CARDIO_TRAINING'");
    }

    @Test
    void shouldReturnAllTrainers(TrainerService trainerService) {
        Trainer trainer1 = new Trainer(3, 14, TrainingType.STRENGTH_TRAINING);
        Trainer trainer2 = new Trainer(4, 15, TrainingType.CARDIO_TRAINING);

        trainerService.createTrainer(trainer1);
        trainerService.createTrainer(trainer2);

        List<Trainer> allTrainers = trainerService.getAllTrainers();

        assertEquals(2, allTrainers.size(), "There should be 2 trainers");
        assertTrue(allTrainers.contains(trainer1), "Trainer 1 should be in the list");
        assertTrue(allTrainers.contains(trainer2), "Trainer 2 should be in the list");
    }
}