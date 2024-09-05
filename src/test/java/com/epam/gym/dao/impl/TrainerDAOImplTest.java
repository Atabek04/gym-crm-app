package com.epam.gym.dao.impl;

import com.epam.gym.dao.TrainerDAO;
import com.epam.gym.dao.impl.prarameterResolver.TrainerDAOParameterResolver;
import com.epam.gym.exception.ResourceNotFoundException;
import com.epam.gym.model.Trainer;
import com.epam.gym.model.TrainingType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(TrainerDAOParameterResolver.class)
class TrainerDAOImplTest {

    @Test
    void shouldSaveAndFindTrainerSuccessfully(TrainerDAO trainerDAO) {
        Trainer trainer = new Trainer(1L, 81L, TrainingType.CARDIO_TRAINING);
        trainerDAO.save(trainer, trainer.getId());

        var foundTrainer = trainerDAO.findById(1L).orElseThrow(() -> new ResourceNotFoundException("Trainer not found"));

        assertAll(
                "Grouped assertions of trainer",
                () -> assertNotNull(foundTrainer, "Trainer should be found after save"),
                () -> assertEquals(1, foundTrainer.getId(), "ID should match the saved trainer"),
                () -> assertEquals(81, foundTrainer.getUserId(), "User ID should match"),
                () -> assertEquals(TrainingType.CARDIO_TRAINING, foundTrainer.getSpecialization(),
                        "Specialization should match")
        );
    }

    @Test
    void shouldUpdateTrainerSpecialization(TrainerDAO trainerDAO) {
        Trainer originalTrainer = new Trainer(1L, 81L, TrainingType.CARDIO_TRAINING);
        trainerDAO.save(originalTrainer, originalTrainer.getId());

        Trainer updatedTrainer = new Trainer(1L, 81L, TrainingType.YOGA);
        trainerDAO.update(updatedTrainer, updatedTrainer.getId());

        var foundTrainer = trainerDAO.findById(1L).orElseThrow(() -> new ResourceNotFoundException("Trainer not found"));

        assertAll(
                "Grouped assertions of trainer",
                () -> assertNotNull(foundTrainer, "Trainer should be found after update"),
                () -> assertEquals(TrainingType.YOGA, foundTrainer.getSpecialization(),
                        "Specialization should be updated")
        );
    }

    @Test
    void shouldFindAllTrainers(TrainerDAO trainerDAO) {
        Trainer trainer1 = new Trainer(1L, 81L, TrainingType.CIRCUIT_TRAINING);
        Trainer trainer2 = new Trainer(2L, 82L, TrainingType.MOBILITY_TRAINING);
        trainerDAO.save(trainer1, trainer1.getId());
        trainerDAO.save(trainer2, trainer2.getId());

        var trainers = trainerDAO.findAll();

        assertAll(
                "Grouped assertions of trainers",
                () -> assertEquals(2, trainers.size(), "Two trainers should be found"),
                () -> assertTrue(trainers.contains(trainer1), "First trainer should be found"),
                () -> assertTrue(trainers.contains(trainer2), "Second trainer should be found")
        );
    }
}