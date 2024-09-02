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
    void shouldSaveAndFindTraineeSuccessfully(TraineeDAO traineeDAO) {
        Trainee trainee = new Trainee(1, 23, LocalDate.of(2000, 12, 2), "Red Rose 12");
        traineeDAO.save(trainee, trainee.getId());

        Optional<Trainee> foundTrainee = traineeDAO.findById(1);

        assertTrue(foundTrainee.isPresent(), "Trainee should be found after save");
        assertEquals(1, foundTrainee.get().getId(), "ID should match the saved trainee");
        assertEquals("Red Rose 12", foundTrainee.get().getAddress(), "Address should match");
    }

    @Test
    void shouldUpdateTraineeAddress(TraineeDAO traineeDAO) {
        Trainee originalTrainee = new Trainee(1, 23, LocalDate.of(2000, 12, 2), "Red Rose 12");
        traineeDAO.save(originalTrainee, originalTrainee.getId());

        Trainee updatedTrainee = new Trainee(1, 23, LocalDate.of(2000, 12, 2), "Blue River 15");
        traineeDAO.update(updatedTrainee, updatedTrainee.getId());

        Optional<Trainee> foundTrainee = traineeDAO.findById(1);

        assertTrue(foundTrainee.isPresent(), "Trainee should be found after update");
        assertEquals("Blue River 15", foundTrainee.get().getAddress(), "Address should be updated");
    }

    @Test
    void shouldReturnEmptyOptionalForNonExistingTrainee(TraineeDAO traineeDAO) {
        Optional<Trainee> foundTrainee = traineeDAO.findById(99);
        assertTrue(foundTrainee.isEmpty(), "No trainee should be found for non-existing ID");
    }

    @Test
    void shouldFindAllTrainees(TraineeDAO traineeDAO) {
        Trainee trainee1 = new Trainee(1, 23, LocalDate.of(2000, 12, 2), "Red Rose 12");
        Trainee trainee2 = new Trainee(2, 25, LocalDate.of(1998, 3, 14), "Blue River 15");

        traineeDAO.save(trainee1, trainee1.getId());
        traineeDAO.save(trainee2, trainee2.getId());

        var allTrainees = traineeDAO.findAll();

        assertEquals(2, allTrainees.size(), "There should be two trainees in the storage");
        assertTrue(allTrainees.contains(trainee1), "Trainee 1 should be in the list");
        assertTrue(allTrainees.contains(trainee2), "Trainee 2 should be in the list");
    }

    @Test
    void shouldDeleteTraineeSuccessfully(TraineeDAO traineeDAO) {
        Trainee trainee = new Trainee(1, 23, LocalDate.of(2000, 12, 2), "Red Rose 12");
        traineeDAO.save(trainee, trainee.getId());
        traineeDAO.delete(1);

        Optional<Trainee> foundTrainee = traineeDAO.findById(1);

        assertTrue(foundTrainee.isEmpty(), "Trainee should be deleted and not found");
    }
}