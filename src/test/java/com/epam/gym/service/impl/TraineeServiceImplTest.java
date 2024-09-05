package com.epam.gym.service.impl;

import com.epam.gym.model.Trainee;
import com.epam.gym.service.TraineeService;
import com.epam.gym.service.impl.parameterResolver.TraineeServiceParameterResolver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(TraineeServiceParameterResolver.class)
class TraineeServiceImplTest {

    @BeforeEach
    void setUp(TraineeService traineeService) {
        traineeService.findAll().forEach(trainee -> traineeService.delete(trainee.getId()));
    }

    @Test
    void shouldCreateTraineeSuccessfully(TraineeService traineeService) {
        Trainee trainee = new Trainee(1L, 23L, LocalDate.of(2000, 12, 2),
                "Red Rose 12");

        traineeService.create(trainee, trainee.getId());

        Trainee foundTrainee = traineeService.findById(1L)
                .orElseThrow(() -> new RuntimeException("Trainee not found"));

        assertAll(
                () -> assertNotNull(foundTrainee, "Trainee should be found"),
                () -> assertEquals(1L, foundTrainee.getId(), "Trainee id should be 1"),
                () -> assertEquals(23L, foundTrainee.getUserId(), "Trainer id should be 23"),
                () -> assertEquals(LocalDate.of(2000, 12, 2), foundTrainee.getDateOfBirth(),
                        "Birth date should be 2000-12-2"),
                () -> assertEquals("Red Rose 12", foundTrainee.getAddress(),
                        "Address should be 'Red Rose 12'")
        );
    }

    @Test
    void shouldUpdateTraineeAddress(TraineeService traineeService) {
        Trainee originalTrainee = new Trainee(1L, 23L, LocalDate.of(2000, 12, 2),
                "Red Rose 12");
        traineeService.create(originalTrainee, originalTrainee.getId());

        Trainee updatedTrainee = new Trainee(1L, 23L, LocalDate.of(2000, 12, 3),
                "Blue River 15");
        traineeService.update(updatedTrainee, updatedTrainee.getId());

        Trainee foundTrainee = traineeService.findById(1L)
                .orElseThrow(() -> new RuntimeException("Trainee not found"));

        assertAll(
                () -> assertNotNull(foundTrainee, "Trainee should be found"),
                () -> assertEquals(1L, foundTrainee.getId(), "Trainee id should be 1"),
                () -> assertEquals(LocalDate.of(2000, 12, 3), foundTrainee.getDateOfBirth(),
                        "Birth date should be 2000-12-3"),
                () -> assertEquals("Blue River 15", foundTrainee.getAddress(),
                        "Address should be 'Blue River 15'")
        );
    }

    @Test
    void shouldReturnAllTrainees(TraineeService traineeService) {
        Trainee trainee1 = new Trainee(1L, 23L, LocalDate.of(2000, 12, 2),
                "Red Rose 12");
        Trainee trainee2 = new Trainee(2L, 25L, LocalDate.of(2000, 12, 2),
                "Blue Rose 21");

        traineeService.create(trainee1, trainee1.getId());
        traineeService.create(trainee2, trainee2.getId());

        List<Trainee> trainees = traineeService.findAll();

        assertAll(
                () -> assertEquals(2, trainees.size(), "There should be 2 trainees"),
                () -> assertTrue(trainees.contains(trainee1), "Trainee 1 should be found"),
                () -> assertTrue(trainees.contains(trainee2), "Trainee 2 should be found")
        );
    }

    @Test
    void shouldDeleteTraineeSuccessfully(TraineeService traineeService) {
        Trainee trainee = new Trainee(1L, 23L, LocalDate.of(2000, 12, 2),
                "Red Rose 12");
        traineeService.create(trainee, trainee.getId());

        traineeService.delete(1L);

        var foundTrainee = traineeService.findById(1L).orElse(null);

        assertNull(foundTrainee, "Trainee should be deleted and not found");
    }
}