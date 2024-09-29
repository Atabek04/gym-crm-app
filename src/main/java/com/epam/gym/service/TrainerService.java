package com.epam.gym.service;

import com.epam.gym.dto.TrainerRequest;
import com.epam.gym.dto.TrainerResponse;
import com.epam.gym.dto.TrainerTrainingFilterRequest;
import com.epam.gym.dto.TrainerUpdateRequest;
import com.epam.gym.dto.TrainingResponse;
import com.epam.gym.dto.UserCredentials;
import com.epam.gym.model.Trainer;

import java.util.List;
import java.util.Optional;

public interface TrainerService {
    Optional<Trainer> create(Trainer trainer);

    void update(Trainer trainer, Long id);

    Optional<Trainer> findById(Long id);

    List<Trainer> findAll();

    List<Trainer> findAllFreeTrainers(String username);

    void delete(Long id);

    void delete(String username);

    UserCredentials create(TrainerRequest request);

    TrainerResponse getTrainerAndTrainees(String username);

    TrainerResponse updateTrainerAndUser(TrainerUpdateRequest request, String username);

    List<TrainingResponse> findTrainerTrainingsByFilters(String username, TrainerTrainingFilterRequest filterRequest);

    void updateTrainerStatus(String username, Boolean active);
}
