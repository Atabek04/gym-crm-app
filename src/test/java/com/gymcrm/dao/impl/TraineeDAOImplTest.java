package com.gymcrm.dao.impl;

import com.gymcrm.dao.TraineeDAO;
import com.gymcrm.dao.impl.prarameterResolver.TraineeDAOParameterResolver;
import com.gymcrm.model.Trainee;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(TraineeDAOParameterResolver.class)
class TraineeDAOImplTest {
    @Test
    void testCreateTrainee(TraineeDAO traineeDAO) {
        Trainee trainee = new Trainee(1, 23,
                LocalDate.of(2000, 12, 2), "Red Rose 12");
        traineeDAO.save(trainee);

        Optional<Trainee> foundTrainee = traineeDAO.findById(1);

        assertTrue(foundTrainee.isPresent(), "Trainee should be found");
        assertEquals(1, foundTrainee.get().getId(), "Trainee ID should be 1");
        assertEquals("Red Rose 12", foundTrainee.get().getAddress(),
                "Address should be 'Red Rose 12'");
    }

    @Test
    void testUpdateTrainee(TraineeDAO traineeDAO) {
        Trainee originalTrainee = new Trainee(1, 23,
                LocalDate.of(2000, 12, 2), "Red Rose 12");

        traineeDAO.save(originalTrainee);

        Trainee updatedTrainee = new Trainee(1, 23,
                LocalDate.of(2000, 12, 2), "Blue River 15");

        traineeDAO.update(updatedTrainee);

        Optional<Trainee> foundTrainee = traineeDAO.findById(1);

        assertTrue(foundTrainee.isPresent(), "Trainee should be found");
        assertEquals("Blue River 15", foundTrainee.get().getAddress(),
                "Address should be 'Blue River 15'");
    }

    @Test
    void testFindByIdTrainee(TraineeDAO traineeDAO) {
        Trainee trainee = new Trainee(1, 23,
                LocalDate.of(2000, 12, 2), "Red Rose 12");

        traineeDAO.save(trainee);

        Optional<Trainee> foundTrainee = traineeDAO.findById(1);

        assertTrue(foundTrainee.isPresent(), "Trainee should be found");
        assertEquals(trainee, foundTrainee.get(), "Found trainee should match the saved trainee");
    }

    @Test
    void testFindAllTrainees(TraineeDAO traineeDAO) {
        Trainee trainee1 = new Trainee(1, 23,
                LocalDate.of(2000, 12, 2), "Red Rose 12");
        Trainee trainee2 = new Trainee(2, 25,
                LocalDate.of(1998, 3, 14), "Blue River 15");

        traineeDAO.save(trainee1);
        traineeDAO.save(trainee2);

        var allTrainees = traineeDAO.findAll();

        assertEquals(2, allTrainees.size(), "There should be two trainees in the storage");
        assertTrue(allTrainees.contains(trainee1), "Trainee1 should be in the list");
        assertTrue(allTrainees.contains(trainee2), "Trainee2 should be in the list");
    }

    @Test
    void testDeleteTrainee(TraineeDAO traineeDAO) {
        Trainee trainee = new Trainee(1, 23,
                LocalDate.of(2000, 12, 2), "Red Rose 12");

        traineeDAO.save(trainee);
        traineeDAO.delete(1);

        Optional<Trainee> foundTrainee = traineeDAO.findById(1);

        assertTrue(foundTrainee.isEmpty(), "Trainee should be deleted and not found");
    }
}