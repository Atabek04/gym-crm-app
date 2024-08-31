package com.gymcrm.service.impl;

import com.gymcrm.model.Trainer;
import com.gymcrm.model.TrainingType;
import com.gymcrm.service.TrainerService;
import com.gymcrm.service.impl.parameterResolver.TrainerServiceParameterResolver;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(TrainerServiceParameterResolver.class)
class TrainerServiceImplTest {
    @Test
    void testCreateTrainer(TrainerService trainerService) {
        Trainer trainer = new Trainer(3, 14, "STRENGTH_TRAINING");

        trainerService.createTrainer(trainer);

        var foundTrainer = trainerService.getTrainer(3);

        assertNotNull(foundTrainer, "Trainer should be found");
        assertEquals(3, foundTrainer.getId(), "Trainer ID should be 3");
    }

    @Test
    void testUpdateTrainer(TrainerService trainerService) {
        Trainer originalTrainer = new Trainer(3, 14, "STRENGTH_TRAINING");

        trainerService.createTrainer(originalTrainer);

        Trainer updatedTrainer = new Trainer(3, 14, "CARDIO_TRAINING");

        trainerService.updateTrainer(updatedTrainer);

        var foundTrainer = trainerService.getTrainer(3);

        assertNotNull(foundTrainer, "Trainer should be found");
        assertEquals(TrainingType.valueOf("CARDIO_TRAINING"), foundTrainer.getSpecialization(),
                "Specialization should be 'CARDIO_TRAINING'");
    }

    @Test
    void testGetTrainer(TrainerService trainerService) {
        Trainer trainer = new Trainer(3, 14, "STRENGTH_TRAINING");

        trainerService.createTrainer(trainer);

        var foundTrainer = trainerService.getTrainer(3);

        assertNotNull(foundTrainer, "Trainer should be found");
        assertEquals(3, foundTrainer.getId(), "Trainer ID should be 3");
    }

    @Test
    void testGetAllTrainer(TrainerService trainerService) {
        Trainer trainer1 = new Trainer(3, 14, "STRENGTH_TRAINING");
        Trainer trainer2 = new Trainer(4, 15, "CARDIO_TRAINING");

        trainerService.createTrainer(trainer1);
        trainerService.createTrainer(trainer2);

        var allTrainers = trainerService.getAllTrainer();

        assertEquals(2, allTrainers.size(), "There should be 2 trainers");
        assertTrue(allTrainers.contains(trainer1), "Trainer 1 should be in the list");
        assertTrue(allTrainers.contains(trainer2), "Trainer 2 should be in the list");
    }
}