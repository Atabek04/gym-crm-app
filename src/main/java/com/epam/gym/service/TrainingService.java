package com.epam.gym.service;

import com.epam.gym.model.Training;

import java.util.List;
import java.util.Optional;

public interface TrainingService {
    void create(Training training);

    void update(Training training, Long id);

    Optional<Training> findById(Long id);

    List<Training> findAll();

    void delete(Long id);
}
