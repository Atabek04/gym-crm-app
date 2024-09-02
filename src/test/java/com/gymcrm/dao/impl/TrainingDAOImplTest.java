package com.gymcrm.dao.impl;

import com.gymcrm.dao.TrainingDAO;
import com.gymcrm.dao.impl.prarameterResolver.TrainingDAOParameterResolver;
import com.gymcrm.model.Training;
import com.gymcrm.model.TrainingType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(TrainingDAOParameterResolver.class)
class TrainingDAOImplTest {
    @Test
    void shouldSaveAndFindTrainingSuccessfully(TrainingDAO trainingDAO) {
        Training training = new Training(1, 81, 82, "Flex and Tone", TrainingType.YOGA,
                LocalDateTime.of(2021, 12, 2, 10, 0), 60);
        trainingDAO.save(training, training.getId());

        var foundTraining = trainingDAO.findById(1);

        assertTrue(foundTraining.isPresent(), "Training should be found after save");
        assertEquals(1, foundTraining.get().getId(), "Training ID should match");
        assertEquals(82, foundTraining.get().getTrainerId(), "Trainer ID should match");
    }

    @Test
    void shouldFindAllTrainings(TrainingDAO trainingDAO) {
        Training training1 = new Training(1, 81, 82, "Flex and Tone", TrainingType.YOGA,
                LocalDateTime.of(2021, 12, 2, 10, 0), 60);
        Training training2 = new Training(2, 82, 83, "Cardio Blast", TrainingType.CARDIO,
                LocalDateTime.of(2021, 12, 2, 11, 0), 45);

        trainingDAO.save(training1, training1.getId());
        trainingDAO.save(training2, training2.getId());

        var trainings = trainingDAO.findAll();

        assertEquals(2, trainings.size(), "There should be two trainings");
        assertTrue(trainings.contains(training1), "Training 1 should be found");
        assertTrue(trainings.contains(training2), "Training 2 should be found");
    }
}