package com.gymcrm.service;

import com.gymcrm.model.Trainee;

import java.util.List;
import java.util.Optional;

public interface TraineeService {
    void createTrainee(Trainee trainee);

    void updateTrainee(Trainee trainee);

    Optional<Trainee> getTrainee(int id);

    List<Trainee> getAllTrainees();

    void deleteTrainee(int id);
}
