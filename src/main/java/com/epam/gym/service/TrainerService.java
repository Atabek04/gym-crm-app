package com.epam.gym.service;

import com.epam.gym.model.Trainer;

import java.util.List;
import java.util.Optional;

public interface TrainerService {
    Optional<Trainer> create(Trainer trainer);

    void update(Trainer trainer, Long id);

    Optional<Trainer> findById(Long id);

    Optional<Trainer> findByUsername(String username);

    List<Trainer> findAll();

    List<Trainer> findAllFreeTrainers(String username);

    void delete(Long id);
}
