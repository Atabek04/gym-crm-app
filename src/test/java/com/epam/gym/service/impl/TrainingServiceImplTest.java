package com.epam.gym.service.impl;

import com.epam.gym.exception.ResourceNotFoundException;
import com.epam.gym.model.Training;
import com.epam.gym.model.TrainingType;
import com.epam.gym.service.TrainingService;
import com.epam.gym.service.impl.parameterResolver.TrainingServiceParameterResolver;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(TrainingServiceParameterResolver.class)
class TrainingServiceImplTest {

    @Test
    void shouldCreateTrainingSuccessfully(TrainingService trainingService) {
        Training training = new Training(1L, 81L, 82L, "Flex and Tone",
                TrainingType.YOGA, LocalDateTime.of(2021, 12, 2, 10, 0), 60);
        trainingService.create(training, training.getId());

        var foundTraining = trainingService.findById(1L).orElseThrow(() -> new ResourceNotFoundException("Training not found"));

        assertAll(
                () -> assertNotNull(foundTraining, "Training should be found"),
                () -> assertEquals(1, foundTraining.getId(), "Training ID should be 1"),
                () -> assertEquals(82, foundTraining.getTrainerId(), "Trainer ID should be 82")
        );
    }

    @Test
    void shouldReturnAllTrainings(TrainingService trainingService) {
        Training training1 = new Training(1L, 81L, 82L, "Flex and Tone",
                TrainingType.YOGA, LocalDateTime.of(2021, 12, 2, 10, 0), 60);
        Training training2 = new Training(2L, 82L, 83L, "Cardio Blast", TrainingType.CARDIO, LocalDateTime.of(2021, 12, 2, 11, 0), 45);

        trainingService.create(training1, training1.getId());
        trainingService.create(training2, training2.getId());

        List<Training> allTrainings = trainingService.findAll();

        assertAll(
                () -> assertEquals(2, allTrainings.size(), "There should be 2 trainings"),
                () -> assertEquals(1, allTrainings.get(0).getId(), "First training ID should be 1"),
                () -> assertEquals(2, allTrainings.get(1).getId(), "Second training ID should be 2")
        );
    }
}