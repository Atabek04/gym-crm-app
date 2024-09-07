package com.epam.gym.service;

import com.epam.gym.model.Trainer;

import java.util.List;
import java.util.Optional;

public interface TrainerService {
    void create(Trainer trainer, Long id);

    void update(Trainer trainer, Long id);

    Optional<Trainer> findById(Long id);

    List<Trainer> findAll();

    void delete(Long id);
}
