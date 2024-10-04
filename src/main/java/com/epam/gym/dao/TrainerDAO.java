package com.epam.gym.dao;

import com.epam.gym.dto.TrainerTrainingFilterRequest;
import com.epam.gym.model.Trainee;
import com.epam.gym.model.Trainer;
import com.epam.gym.model.Training;

import java.util.List;
import java.util.Optional;

public interface TrainerDAO extends BaseDAO<Trainer> {
    List<Trainer> findAllFreeTrainers(String username);

    Optional<Trainer> findByUsername(String username);

    List<Trainee> getAssignedTrainees(String username);

    List<Training> findTrainerTrainingsByFilters(String username, TrainerTrainingFilterRequest filterRequest);
}
