package com.gymcrm.service.impl;

import com.gymcrm.model.Trainee;
import com.gymcrm.service.TraineeService;
import com.gymcrm.service.impl.parameterResolver.TraineeServiceParameterResolver;
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
        traineeService.getAllTrainees().forEach(trainee -> traineeService.deleteTrainee(trainee.getId()));
    }

    @Test
    void shouldCreateTraineeSuccessfully(TraineeService traineeService) {
        Trainee trainee = new Trainee(1, 23, LocalDate.of(2000, 12, 2),
                "Red Rose 12");

        traineeService.createTrainee(trainee);

        Trainee foundTrainee = traineeService.getTrainee(1)
                .orElseThrow(() -> new RuntimeException("Trainee not found"));

        assertNotNull(foundTrainee, "Trainee should be found");
        assertEquals(1, foundTrainee.getId(), "Trainee ID should be 1");
        assertEquals("Red Rose 12", foundTrainee.getAddress(), "Address should be 'Red Rose 12'");
    }

    @Test
    void shouldUpdateTraineeAddress(TraineeService traineeService) {
        Trainee originalTrainee = new Trainee(1, 23, LocalDate.of(2000, 12, 2),
                "Red Rose 12");
        traineeService.createTrainee(originalTrainee);

        Trainee updatedTrainee = new Trainee(1, 23, LocalDate.of(2000, 12, 2),
                "Blue River 15");
        traineeService.updateTrainee(updatedTrainee);

        Trainee foundTrainee = traineeService.getTrainee(1)
                .orElseThrow(() -> new RuntimeException("Trainee not found"));

        assertNotNull(foundTrainee, "Trainee should be found");
        assertEquals("Blue River 15", foundTrainee.getAddress(),
                "Address should be updated to 'Blue River 15'");
    }

    @Test
    void shouldReturnAllTrainees(TraineeService traineeService) {
        Trainee trainee1 = new Trainee(1, 23, LocalDate.of(2000, 12, 2),
                "Red Rose 12");
        Trainee trainee2 = new Trainee(2, 24, LocalDate.of(2001, 1, 5),
                "Blue River 15");

        traineeService.createTrainee(trainee1);
        traineeService.createTrainee(trainee2);

        List<Trainee> trainees = traineeService.getAllTrainees();

        assertEquals(2, trainees.size(), "Number of trainees should be 2");
        assertTrue(trainees.contains(trainee1), "Trainee 1 should be in the list");
        assertTrue(trainees.contains(trainee2), "Trainee 2 should be in the list");
    }

    @Test
    void shouldDeleteTraineeSuccessfully(TraineeService traineeService) {
        Trainee trainee = new Trainee(1, 23, LocalDate.of(2000, 12, 2),
                "Red Rose 12");
        traineeService.createTrainee(trainee);

        traineeService.deleteTrainee(1);

        var foundTrainee = traineeService.getTrainee(1).orElse(null);

        assertNull(foundTrainee, "Trainee should be deleted and not found");
    }
}