package com.epam.gym.dao.impl;

import com.epam.gym.dao.TraineeDAO;
import com.epam.gym.dao.impl.prarameterResolver.TraineeDAOParameterResolver;
import com.epam.gym.exception.ResourceNotFoundException;
import com.epam.gym.model.Trainee;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(TraineeDAOParameterResolver.class)
class TraineeDAOImplTest {

    @Test
    void shouldSaveAndFindTraineeSuccessfully(TraineeDAO traineeDAO) {
        Trainee trainee = new Trainee(1L, 23L,
                LocalDate.of(2000, 12, 2), "Red Rose 12");
        traineeDAO.save(trainee, trainee.getId());

        var foundTrainee = traineeDAO.findById(1L).orElseThrow(() -> new ResourceNotFoundException("Trainee not found"));

        assertAll(
                "Grouped assertions of trainee",
                () -> assertNotNull(foundTrainee, "Trainee should be found after save"),
                () -> assertEquals(1, foundTrainee.getId(), "ID should match the saved trainee"),
                () -> assertEquals("Red Rose 12", foundTrainee.getAddress(), "Address should match")
        );
    }

    @Test
    void shouldUpdateTraineeAddress(TraineeDAO traineeDAO) {
        Trainee originalTrainee = new Trainee(1L, 23L,
                LocalDate.of(2000, 12, 2), "Red Rose 12");
        traineeDAO.save(originalTrainee, originalTrainee.getId());

        Trainee updatedTrainee = new Trainee(1L, 23L,
                LocalDate.of(2000, 12, 2), "Blue River 15");
        traineeDAO.update(updatedTrainee, updatedTrainee.getId());

        var foundTrainee = traineeDAO.findById(1L).orElseThrow(() -> new ResourceNotFoundException("Trainee not found"));

        assertAll(
                "Grouped assertions of trainee",
                () -> assertNotNull(foundTrainee, "Trainee should be found after update"),
                () -> assertEquals("Blue River 15", foundTrainee.getAddress(), "Address should be updated")
        );
    }

    @Test
    void shouldReturnEmptyOptionalForNonExistingTrainee(TraineeDAO traineeDAO) {
        Optional<Trainee> foundTrainee = traineeDAO.findById(99L);
        assertTrue(foundTrainee.isEmpty(), "No trainee should be found for non-existing ID");
    }

    @Test
    void shouldFindAllTrainees(TraineeDAO traineeDAO) {
        Trainee trainee1 = new Trainee(1L, 23L,
                LocalDate.of(2000, 12, 2), "Red Rose 12");
        Trainee trainee2 = new Trainee(2L, 25L,
                LocalDate.of(1998, 3, 14), "Blue River 15");

        traineeDAO.save(trainee1, trainee1.getId());
        traineeDAO.save(trainee2, trainee2.getId());

        var allTrainees = traineeDAO.findAll();

        assertAll(
                "Grouped assertions of trainees",
                () -> assertEquals(2, allTrainees.size(), "Two trainees should be found"),
                () -> assertTrue(allTrainees.contains(trainee1), "First trainee should be found"),
                () -> assertTrue(allTrainees.contains(trainee2), "Second trainee should be found")
        );
    }

    @Test
    void shouldDeleteTraineeSuccessfully(TraineeDAO traineeDAO) {
        Trainee trainee = new Trainee(1L, 23L,
                LocalDate.of(2000, 12, 2), "Red Rose 12");
        traineeDAO.save(trainee, trainee.getId());
        traineeDAO.delete(1L);

        Optional<Trainee> foundTrainee = traineeDAO.findById(1L);

        assertTrue(foundTrainee.isEmpty(), "Trainee should be deleted and not found");
    }
}