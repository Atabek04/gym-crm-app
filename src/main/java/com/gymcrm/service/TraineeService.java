package com.gymcrm.service;

import com.gymcrm.model.Trainee;

import java.util.List;

public interface TraineeService {
    void createTrainee(Trainee trainee);

    void updateTrainee(Trainee trainee);

    Trainee getTrainee(int id);

    List<Trainee> getAllTrainees();

    void deleteTrainee(int id);
}
