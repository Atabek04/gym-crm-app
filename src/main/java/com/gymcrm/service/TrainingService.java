package com.gymcrm.service;

import com.gymcrm.model.Training;

import java.util.List;

public interface TrainingService {
    void createTraining(Training training);

    Training getTraining(int id);

    List<Training> getAllTrainings();
}
