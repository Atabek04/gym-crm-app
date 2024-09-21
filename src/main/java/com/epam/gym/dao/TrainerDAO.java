package com.epam.gym.dao;

import com.epam.gym.model.Trainer;

import java.util.List;
import java.util.Optional;

public interface TrainerDAO extends BaseDAO<Trainer> {
    List<Trainer> findAllFreeTrainers(String username);

    Optional<Trainer> findByUsername(String username);
}
