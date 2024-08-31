package com.gymcrm.service.impl;

import com.gymcrm.model.Trainee;
import com.gymcrm.service.TraineeService;
import com.gymcrm.service.impl.parameterResolver.TraineeServiceParameterResolver;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(TraineeServiceParameterResolver.class)
class TraineeServiceImplTest {
    @Test
    void testCreateTrainee(TraineeService traineeService) {
        Trainee trainee = new Trainee(1, 23,
                LocalDate.of(2000, 12, 2), "Red Rose 12");

        traineeService.createTrainee(trainee);

        var foundTrainee = traineeService.getTrainee(1);

        assertNotNull(foundTrainee, "Trainee should be found");
        assertEquals(1, foundTrainee.getId(), "Trainee ID should be 1");
        assertEquals("Red Rose 12", foundTrainee.getAddress(),
                "Address should be 'Red Rose 12'");
    }

    @Test
    void testUpdateTrainee(TraineeService traineeService) {
        Trainee originalTrainee = new Trainee(1, 23,
                LocalDate.of(2000, 12, 2), "Red Rose 12");

        traineeService.createTrainee(originalTrainee);

        Trainee updatedTrainee = new Trainee(1, 23,
                LocalDate.of(2000, 12, 2), "Blue River 15");

        traineeService.updateTrainee(updatedTrainee);

        var foundTrainee = traineeService.getTrainee(1);

        assertNotNull(foundTrainee, "Trainee should be found");
        assertEquals("Blue River 15", foundTrainee.getAddress(),
                "Address should be 'Blue River 15'");
    }

    @Test
    void testGetTrainee(TraineeService traineeService) {
        Trainee trainee = new Trainee(1, 23,
                LocalDate.of(2000, 12, 2), "Red Rose 12");

        traineeService.createTrainee(trainee);

        var foundTrainee = traineeService.getTrainee(1);

        assertNotNull(foundTrainee, "Trainee should be found");
        assertEquals(1, foundTrainee.getId(), "Trainee ID should be 1");
        assertEquals("Red Rose 12", foundTrainee.getAddress(),
                "Address should be 'Red Rose 12'");
    }

    @Test
    void testGetAllTrainees(TraineeService traineeService) {
        Trainee trainee1 = new Trainee(1, 23,
                LocalDate.of(2000, 12, 2), "Red Rose 12");
        Trainee trainee2 = new Trainee(2, 24,
                LocalDate.of(2001, 1, 5), "Blue River 15");

        traineeService.createTrainee(trainee1);
        traineeService.createTrainee(trainee2);

        var trainees = traineeService.getAllTrainees();

        assertEquals(2, trainees.size(), "Number of trainees should be 2");
        assertEquals(trainee1, trainees.get(0), "Trainee 1 should be found");
        assertEquals(trainee2, trainees.get(1), "Trainee 2 should be found");
    }

    @Test
    void testDeleteTrainee(TraineeService traineeService) {
        Trainee trainee = new Trainee(1, 23,
                LocalDate.of(2000, 12, 2), "Red Rose 12");

        traineeService.createTrainee(trainee);

        traineeService.deleteTrainee(1);

        var foundTrainee = traineeService.getTrainee(1);

        assertNull(foundTrainee, "Trainee should not be found");
    }
}