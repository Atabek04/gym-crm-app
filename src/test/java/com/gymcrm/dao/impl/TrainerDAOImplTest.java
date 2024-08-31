package com.gymcrm.dao.impl;

import com.gymcrm.dao.TrainerDAO;
import com.gymcrm.dao.impl.prarameterResolver.TrainerDAOParameterResolver;
import com.gymcrm.model.Trainer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(TrainerDAOParameterResolver.class)
class TrainerDAOImplTest {
    @Test
    void testSaveTrainer(TrainerDAO trainerDAO) {
        Trainer trainer = new Trainer(1, 81, "STRENGTH_TRAINING");
        trainerDAO.save(trainer);

        var foundTrainer = trainerDAO.findById(1);

        assertTrue(foundTrainer.isPresent(), "Trainer should be found");
        assertEquals(1, foundTrainer.get().getId(), "Trainer ID should be 1");
        assertEquals(81, foundTrainer.get().getUserId(), "User ID should be 81");
    }

    @Test
    void testUpdateTrainer(TrainerDAO trainerDAO) {
        Trainer originalTrainer = new Trainer(1, 81, "STRENGTH_TRAINING");
        trainerDAO.save(originalTrainer);

        Trainer updatedTrainer = new Trainer(1, 81, "CARDIO_TRAINING");
        trainerDAO.update(updatedTrainer);

        var foundTrainer = trainerDAO.findById(1);

        assertTrue(foundTrainer.isPresent(), "Trainer should be found");
        assertEquals("CARDIO_TRAINING", foundTrainer.get().getSpecialization().toString(),
                "Specialization should be 'CARDIO_TRAINING'");
    }

    @Test
    void testFindTrainer(TrainerDAO trainerDAO) {
        Trainer trainer = new Trainer(1, 81, "STRENGTH_TRAINING");
        trainerDAO.save(trainer);

        Optional<Trainer> foundTrainer = trainerDAO.findById(1);

        assertTrue(foundTrainer.isPresent(), "Trainer should be found");
        assertEquals(1, foundTrainer.get().getId(), "Trainer ID should be 1");
        assertEquals(81, foundTrainer.get().getUserId(), "User ID should be 81");
    }

    @Test
    void findAll(TrainerDAO trainerDAO) {
        Trainer trainer1 = new Trainer(1, 81, "STRENGTH_TRAINING");
        Trainer trainer2 = new Trainer(2, 82, "CARDIO_TRAINING");
        trainerDAO.save(trainer1);
        trainerDAO.save(trainer2);

        var trainers = trainerDAO.findAll();
        assertEquals(2, trainers.size(), "Number of trainers should be 2");
        assertTrue(trainers.contains(trainer1), "Trainer 1 should be found");
        assertTrue(trainers.contains(trainer2), "Trainer 2 should be found");
    }
}