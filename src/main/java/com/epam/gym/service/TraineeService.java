package com.epam.gym.service;


import com.epam.gym.model.Trainee;

import java.util.List;
import java.util.Optional;

public interface TraineeService {
    Optional<Trainee> create(Trainee trainee);

    void update(Trainee trainee, Long id);

    Optional<Trainee> findById(Long id);

    List<Trainee> findAll();

    void delete(Long id);
}
