package com.gymcrm.service;

import com.gymcrm.model.Trainer;

import java.util.List;
import java.util.Optional;

public interface TrainerService {
    void createTrainer(Trainer trainer);

    void updateTrainer(Trainer trainer);

    Trainer getTrainer(int id);

    List<Trainer> getAllTrainer();

    void deleteTrainer(int id);
}
