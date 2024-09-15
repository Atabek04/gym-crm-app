package com.epam.gym.service;

import com.epam.gym.dao.TrainerDAO;
import com.epam.gym.exception.ResourceNotFoundException;
import com.epam.gym.model.Trainer;
import com.epam.gym.service.impl.TrainerServiceImpl;
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

class TrainerServiceTest {

    @Mock
    private TrainerDAO trainerDAO;

    @InjectMocks
    private TrainerServiceImpl trainerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateTrainer() {
        Trainer trainer = new Trainer();
        when(trainerDAO.save(trainer)).thenReturn(Optional.of(trainer));

        Optional<Trainer> createdTrainer = trainerService.create(trainer);

        assertTrue(createdTrainer.isPresent());
        assertEquals(trainer, createdTrainer.get());
        verify(trainerDAO, times(1)).save(trainer);
    }

    @Test
    void testUpdateTrainerSuccess() {
        Long trainerId = 1L;
        Trainer existingTrainer = new Trainer();
        existingTrainer.setId(trainerId);
        when(trainerDAO.findById(trainerId)).thenReturn(Optional.of(existingTrainer));

        Trainer updatedTrainer = new Trainer();
        updatedTrainer.setId(trainerId);

        trainerService.update(updatedTrainer, trainerId);

        verify(trainerDAO, times(1)).findById(trainerId);
        verify(trainerDAO, times(1)).update(updatedTrainer, trainerId);
    }

    @Test
    void testUpdateTrainerNotFound() {
        Long trainerId = 1L;
        Trainer trainer = new Trainer();
        trainer.setId(trainerId);
        when(trainerDAO.findById(trainerId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> trainerService.update(trainer, trainerId));
        verify(trainerDAO, times(1)).findById(trainerId);
        verify(trainerDAO, times(0)).update(trainer, trainerId);
    }

    @Test
    void testFindTrainerByIdSuccess() {
        Long trainerId = 1L;
        Trainer trainer = new Trainer();
        trainer.setId(trainerId);
        when(trainerDAO.findById(trainerId)).thenReturn(Optional.of(trainer));

        Optional<Trainer> foundTrainer = trainerService.findById(trainerId);

        assertTrue(foundTrainer.isPresent());
        assertEquals(trainerId, foundTrainer.get().getId());
        verify(trainerDAO, times(1)).findById(trainerId);
    }

    @Test
    void testFindTrainerByIdNotFound() {
        Long trainerId = 1L;
        when(trainerDAO.findById(trainerId)).thenReturn(Optional.empty());

        Optional<Trainer> foundTrainer = trainerService.findById(trainerId);

        assertTrue(foundTrainer.isEmpty());
        verify(trainerDAO, times(1)).findById(trainerId);
    }

    @Test
    void testFindAllTrainers() {
        List<Trainer> trainers = List.of(new Trainer(), new Trainer());
        when(trainerDAO.findAll()).thenReturn(trainers);

        List<Trainer> foundTrainers = trainerService.findAll();

        assertEquals(2, foundTrainers.size());
        verify(trainerDAO, times(1)).findAll();
    }

    @Test
    void testFindAllTrainersEmpty() {
        when(trainerDAO.findAll()).thenReturn(Collections.emptyList());

        List<Trainer> foundTrainers = trainerService.findAll();

        assertTrue(foundTrainers.isEmpty());
        verify(trainerDAO, times(1)).findAll();
    }

    @Test
    void testFindAllFreeTrainers() {
        String username = "someUsername";
        List<Trainer> freeTrainers = List.of(new Trainer(), new Trainer());
        when(trainerDAO.findAllFreeTrainers(username)).thenReturn(freeTrainers);

        List<Trainer> foundFreeTrainers = trainerService.findAllFreeTrainers(username);

        assertEquals(2, foundFreeTrainers.size());
        verify(trainerDAO, times(1)).findAllFreeTrainers(username);
    }

    @Test
    void testDeleteTrainer() {
        Long trainerId = 1L;

        trainerService.delete(trainerId);

        verify(trainerDAO, times(1)).delete(trainerId);
    }
}
