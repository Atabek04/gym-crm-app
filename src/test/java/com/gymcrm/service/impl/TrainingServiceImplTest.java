package com.gymcrm.service.impl;

import com.gymcrm.model.Training;
import com.gymcrm.model.TrainingType;
import com.gymcrm.service.TrainingService;
import com.gymcrm.service.impl.parameterResolver.TrainingServiceParameterResolver;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(TrainingServiceParameterResolver.class)
class TrainingServiceImplTest {

    @Test
    void shouldCreateTrainingSuccessfully(TrainingService trainingService) {
        Training training = new Training(1, 81, 82, "Flex and Tone", TrainingType.YOGA, LocalDateTime.of(2021, 12, 2, 10, 0), 60);
        trainingService.create(training, training.getId());

        var foundTraining = trainingService.findById(1).orElse(null);

        assertNotNull(foundTraining, "Training should be found");
        assertEquals(1, foundTraining.getId(), "Training ID should be 1");
        assertEquals(82, foundTraining.getTrainerId(), "Trainer ID should be 82");
    }

    @Test
    void shouldReturnAllTrainings(TrainingService trainingService) {
        Training training1 = new Training(1, 81, 82, "Flex and Tone", TrainingType.YOGA, LocalDateTime.of(2021, 12, 2, 10, 0), 60);
        Training training2 = new Training(2, 82, 83, "Cardio Blast", TrainingType.CARDIO, LocalDateTime.of(2021, 12, 2, 11, 0), 45);

        trainingService.create(training1, training1.getId());
        trainingService.create(training2, training2.getId());

        List<Training> allTrainings = trainingService.findAll();

        assertEquals(2, allTrainings.size(), "There should be 2 trainings");
        assertTrue(allTrainings.contains(training1), "Training 1 should be in the list");
        assertTrue(allTrainings.contains(training2), "Training 2 should be in the list");
    }
}