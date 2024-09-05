package com.epam.gym.service;


import com.epam.gym.model.Trainee;

import java.util.List;
import java.util.Optional;

public interface TraineeService {
    void create(Trainee trainee, Long id);

    void update(Trainee trainee, Long id);

    Optional<Trainee> findById(Long id);

    List<Trainee> findAll();

    void delete(Long id);
}
