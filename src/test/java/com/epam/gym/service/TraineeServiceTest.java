package com.epam.gym.service;

import com.epam.gym.dao.TraineeDAO;
import com.epam.gym.exception.ResourceNotFoundException;
import com.epam.gym.model.Trainee;
import com.epam.gym.service.impl.TraineeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class TraineeServiceTest {

    @Mock
    private TraineeDAO traineeDAO;

    @InjectMocks
    private TraineeServiceImpl traineeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateTrainee() {
        Trainee trainee = new Trainee();
        when(traineeDAO.save(trainee)).thenReturn(Optional.of(trainee));

        Optional<Trainee> createdTrainee = traineeService.create(trainee);

        assertTrue(createdTrainee.isPresent());
        assertEquals(trainee, createdTrainee.get());
        verify(traineeDAO, times(1)).save(trainee);
    }

    @Test
    void testUpdateTraineeSuccess() {
        Long traineeId = 1L;
        Trainee existingTrainee = new Trainee();
        existingTrainee.setId(traineeId);
        when(traineeDAO.findById(traineeId)).thenReturn(Optional.of(existingTrainee));

        Trainee updatedTrainee = new Trainee();
        updatedTrainee.setId(traineeId);

        traineeService.update(updatedTrainee, traineeId);

        verify(traineeDAO, times(1)).findById(traineeId);
        verify(traineeDAO, times(1)).update(updatedTrainee, traineeId);
    }

    @Test
    void testUpdateTraineeNotFound() {
        Long traineeId = 1L;
        Trainee trainee = new Trainee();
        trainee.setId(traineeId);
        when(traineeDAO.findById(traineeId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> traineeService.update(trainee, traineeId));
        verify(traineeDAO, times(1)).findById(traineeId);
        verify(traineeDAO, times(0)).update(trainee, traineeId);
    }

    @Test
    void testFindTraineeByIdSuccess() {
        Long traineeId = 1L;
        Trainee trainee = new Trainee();
        trainee.setId(traineeId);
        when(traineeDAO.findById(traineeId)).thenReturn(Optional.of(trainee));

        Optional<Trainee> foundTrainee = traineeService.findById(traineeId);

        assertTrue(foundTrainee.isPresent());
        assertEquals(traineeId, foundTrainee.get().getId());
        verify(traineeDAO, times(1)).findById(traineeId);
    }

    @Test
    void testFindTraineeByIdNotFound() {
        Long traineeId = 1L;
        when(traineeDAO.findById(traineeId)).thenReturn(Optional.empty());

        Optional<Trainee> foundTrainee = traineeService.findById(traineeId);

        assertTrue(foundTrainee.isEmpty());
        verify(traineeDAO, times(1)).findById(traineeId);
    }

    @Test
    void testFindAllTrainees() {
        List<Trainee> trainees = List.of(new Trainee(), new Trainee());
        when(traineeDAO.findAll()).thenReturn(trainees);

        List<Trainee> foundTrainees = traineeService.findAll();

        assertEquals(2, foundTrainees.size());
        verify(traineeDAO, times(1)).findAll();
    }

    @Test
    void testFindAllTraineesEmpty() {
        when(traineeDAO.findAll()).thenReturn(Collections.emptyList());

        List<Trainee> foundTrainees = traineeService.findAll();

        assertTrue(foundTrainees.isEmpty());
        verify(traineeDAO, times(1)).findAll();
    }

    @Test
    void testDeleteTrainee() {
        Long traineeId = 1L;

        traineeService.delete(traineeId);

        verify(traineeDAO, times(1)).delete(traineeId);
    }
}