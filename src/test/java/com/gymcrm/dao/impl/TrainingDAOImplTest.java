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
    void testSaveTraining(TrainingDAO trainingDAO) {
        Training training = new Training(1, 81, 82, "Flex and Tone",
                TrainingType.valueOf("YOGA"),
                LocalDateTime.of(2021, 12, 2, 10, 0), 60);

        trainingDAO.save(training);

        var foundTraining = trainingDAO.findById(1);

        assertTrue(foundTraining.isPresent(), "Training should be found");
        assertEquals(1, foundTraining.get().getId(), "Training ID should be 1");
        assertEquals(82, foundTraining.get().getTrainerId(), "Trainer ID should be 82");
        assertEquals(60, foundTraining.get().getTrainingDuration(), "Training duration should be 60");
    }

    @Test
    void testFindTraining(TrainingDAO trainingDAO) {
        Training training = new Training(1, 81, 82, "Flex and Tone",
                TrainingType.valueOf("YOGA"),
                LocalDateTime.of(2021, 12, 2, 10, 0), 60);

        trainingDAO.save(training);

        var foundTraining = trainingDAO.findById(1);

        assertTrue(foundTraining.isPresent(), "Training should be found");
        assertEquals(1, foundTraining.get().getId(), "Training ID should be 1");
        assertEquals(82, foundTraining.get().getTrainerId(), "Trainer ID should be 82");
        assertEquals(60, foundTraining.get().getTrainingDuration(), "Training duration should be 60");
    }

    @Test
    void testFindAll(TrainingDAO trainingDAO) {
        Training training1 = new Training(1, 81, 82, "Flex and Tone",
                TrainingType.valueOf("YOGA"),
                LocalDateTime.of(2021, 12, 2, 10, 0), 60);
        Training training2 = new Training(2, 82, 83, "Cardio Blast",
                TrainingType.valueOf("CARDIO"),
                LocalDateTime.of(2021, 12, 2, 11, 0), 45);
        trainingDAO.save(training1);
        trainingDAO.save(training2);

        var trainings = trainingDAO.findAll();

        assertEquals(2, trainings.size(), "Number of trainings should be 2");
        assertTrue(trainings.contains(training1), "Training 1 should be found");
        assertTrue(trainings.contains(training2), "Training 2 should be found");
    }
}