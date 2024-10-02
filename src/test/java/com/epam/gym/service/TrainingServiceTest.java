package com.epam.gym.service;

import com.epam.gym.dao.TrainingDAO;
import com.epam.gym.model.Training;
import com.epam.gym.service.impl.TrainingServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class TrainingServiceTest {

    @Mock
    private TrainingDAO trainingDAO;

    @InjectMocks
    private TrainingServiceImpl trainingService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void testUpdateTrainingSuccess() {
        Long trainingId = 1L;
        Training existingTraining = new Training();
        existingTraining.setId(trainingId);
        when(trainingDAO.findById(trainingId)).thenReturn(Optional.of(existingTraining));

        Training updatedTraining = new Training();
        updatedTraining.setId(trainingId);

        trainingService.update(updatedTraining, trainingId);

        verify(trainingDAO, times(1)).update(updatedTraining, trainingId);
    }

    @Test
    void testFindTrainingByIdSuccess() {
        Long trainingId = 1L;
        Training training = new Training();
        training.setId(trainingId);
        when(trainingDAO.findById(trainingId)).thenReturn(Optional.of(training));

        Optional<Training> foundTraining = trainingService.findById(trainingId);

        assertTrue(foundTraining.isPresent());
        assertEquals(trainingId, foundTraining.get().getId());
        verify(trainingDAO, times(1)).findById(trainingId);
    }

    @Test
    void testFindTrainingByIdNotFound() {
        Long trainingId = 1L;
        when(trainingDAO.findById(trainingId)).thenReturn(Optional.empty());

        Optional<Training> foundTraining = trainingService.findById(trainingId);

        assertTrue(foundTraining.isEmpty());
        verify(trainingDAO, times(1)).findById(trainingId);
    }

    @Test
    void testFindAllTrainings() {
        List<Training> trainings = List.of(new Training(), new Training());
        when(trainingDAO.findAll()).thenReturn(trainings);

        List<Training> foundTrainings = trainingService.findAll();

        assertEquals(2, foundTrainings.size());
        verify(trainingDAO, times(1)).findAll();
    }

    @Test
    void testFindAllTrainingsEmpty() {
        when(trainingDAO.findAll()).thenReturn(Collections.emptyList());

        List<Training> foundTrainings = trainingService.findAll();

        assertTrue(foundTrainings.isEmpty());
        verify(trainingDAO, times(1)).findAll();
    }

    @Test
    void testFindTrainingsByCriteria() {
        Long trainerId = 1L;
        Long traineeId = 2L;
        LocalDate startDate = LocalDate.of(2023, 1, 1);
        LocalDate endDate = LocalDate.of(2023, 12, 31);
        Integer typeId = 1;
        String sortBy = "trainingDate";
        boolean ascending = true;

        List<Training> trainings = List.of(new Training(), new Training());
        when(trainingDAO.findTrainingsByCriteria(trainerId, traineeId, startDate, endDate, typeId, sortBy, ascending))
                .thenReturn(trainings);

        List<Training> foundTrainings = trainingService.findTrainingsByCriteria(trainerId, traineeId, startDate, endDate, typeId, sortBy, ascending);

        assertEquals(2, foundTrainings.size());
        verify(trainingDAO, times(1)).findTrainingsByCriteria(trainerId, traineeId, startDate, endDate, typeId, sortBy, ascending);
    }

    @Test
    void testDeleteTraining() {
        Long trainingId = 1L;

        trainingService.delete(trainingId);

        verify(trainingDAO, times(1)).delete(trainingId);
    }
}
