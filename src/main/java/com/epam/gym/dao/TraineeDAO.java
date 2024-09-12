package com.epam.gym.dao;

import com.epam.gym.model.Trainee;

import java.util.Optional;

public interface TraineeDAO extends BaseDAO<Trainee> {
    Optional<Trainee> findByUsername(String username);
}
