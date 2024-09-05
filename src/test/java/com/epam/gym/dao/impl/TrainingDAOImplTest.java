package com.epam.gym.dao.impl;

import com.epam.gym.dao.TrainingDAO;
import com.epam.gym.dao.impl.prarameterResolver.TrainingDAOParameterResolver;
import com.epam.gym.exception.ResourceNotFoundException;
import com.epam.gym.model.Training;
import com.epam.gym.model.TrainingType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(TrainingDAOParameterResolver.class)
class TrainingDAOImplTest {
    @Test
    void shouldSaveAndFindTrainingSuccessfully(TrainingDAO trainingDAO) {
        Training training = new Training(1L, 81L, 82L, "Flex and Tone", TrainingType.YOGA,
                LocalDateTime.of(2021, 12, 2, 10, 0), 60);
        trainingDAO.save(training, training.getId());

        var foundTraining = trainingDAO.findById(1L).orElseThrow(() -> new ResourceNotFoundException("Training not found"));

        assertAll(
                "Grouped assertions of training",
                () -> assertNotNull(foundTraining, "Training should be found after save"),
                () -> assertEquals(1, foundTraining.getId(), "ID should match the saved training"),
                () -> assertEquals("Flex and Tone", foundTraining.getTrainingName(), "Name should match")
        );
    }

    @Test
    void shouldFindAllTrainings(TrainingDAO trainingDAO) {
        Training training1 = new Training(1L, 81L, 82L, "Flex and Tone", TrainingType.YOGA,
                LocalDateTime.of(2021, 12, 2, 10, 0), 60);
        Training training2 = new Training(2L, 82L, 83L, "Cardio Blast", TrainingType.CARDIO,
                LocalDateTime.of(2021, 12, 2, 11, 0), 45);

        trainingDAO.save(training1, training1.getId());
        trainingDAO.save(training2, training2.getId());

        var trainings = trainingDAO.findAll();

        assertEquals(2, trainings.size(), "There should be two trainings");
        assertTrue(trainings.contains(training1), "Training 1 should be found");
        assertTrue(trainings.contains(training2), "Training 2 should be found");
    }
}