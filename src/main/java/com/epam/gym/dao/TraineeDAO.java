package com.epam.gym.dao;

import com.epam.gym.model.Trainee;
import com.epam.gym.model.Trainer;

import java.util.List;
import java.util.Optional;

public interface TraineeDAO extends BaseDAO<Trainee> {
    Optional<Trainee> findByUsername(String username);

    List<Optional<Trainer>> getAssignedTrainers(String username);
}
