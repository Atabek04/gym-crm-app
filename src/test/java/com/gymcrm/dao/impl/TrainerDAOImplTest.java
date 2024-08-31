package com.gymcrm.dao.impl;

import com.gymcrm.dao.TrainerDAO;
import com.gymcrm.dao.impl.prarameterResolver.TrainerDAOParameterResolver;
import com.gymcrm.model.Trainer;
import com.gymcrm.model.TrainingType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(TrainerDAOParameterResolver.class)
class TrainerDAOImplTest {

    @Test
    void shouldSaveAndFindTrainerSuccessfully(TrainerDAO trainerDAO) {
        Trainer trainer = new Trainer(1, 81, TrainingType.CARDIO_TRAINING);
        trainerDAO.save(trainer);

        var foundTrainer = trainerDAO.findById(1);

        assertTrue(foundTrainer.isPresent(), "Trainer should be found after save");
        assertEquals(1, foundTrainer.get().getId(), "Trainer ID should match");
        assertEquals(81, foundTrainer.get().getUserId(), "User ID should match");
    }

    @Test
    void shouldUpdateTrainerSpecialization(TrainerDAO trainerDAO) {
        Trainer originalTrainer = new Trainer(1, 81, TrainingType.YOGA);
        trainerDAO.save(originalTrainer);

        Trainer updatedTrainer = new Trainer(1, 81, TrainingType.CARDIO_TRAINING);
        trainerDAO.update(updatedTrainer);

        var foundTrainer = trainerDAO.findById(1);

        assertTrue(foundTrainer.isPresent(), "Trainer should be found after update");
        assertEquals(TrainingType.CARDIO_TRAINING, foundTrainer.get().getSpecialization(),
                "Specialization should be updated");
    }

    @Test
    void shouldFindAllTrainers(TrainerDAO trainerDAO) {
        Trainer trainer1 = new Trainer(1, 81, TrainingType.CIRCUIT_TRAINING);
        Trainer trainer2 = new Trainer(2, 82, TrainingType.MOBILITY_TRAINING);
        trainerDAO.save(trainer1);
        trainerDAO.save(trainer2);

        var trainers = trainerDAO.findAll();

        assertEquals(2, trainers.size(), "There should be two trainers");
        assertTrue(trainers.contains(trainer1), "Trainer 1 should be found");
        assertTrue(trainers.contains(trainer2), "Trainer 2 should be found");
    }
}