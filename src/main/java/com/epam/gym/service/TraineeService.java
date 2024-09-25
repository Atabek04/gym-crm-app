package com.epam.gym.service;


import com.epam.gym.dto.TraineeRequest;
import com.epam.gym.dto.TraineeResponse;
import com.epam.gym.dto.TraineeUpdateRequest;
import com.epam.gym.dto.UserCredentials;
import com.epam.gym.model.Trainee;
import com.epam.gym.model.Trainer;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface TraineeService {
    @Transactional
    UserCredentials create(TraineeRequest request);

    Optional<Trainee> create(Trainee trainee);

    void update(Trainee trainee, Long id);

    TraineeResponse updateTraineeAndUser(TraineeUpdateRequest request, String username);

    Optional<Trainee> findById(Long id);

    Optional<Trainee> findByUsername(String username);

    @Transactional
    TraineeResponse getTraineeAndTrainers(String username);

    List<Optional<Trainer>> getAssignedTrainers(String username);

    List<Trainee> findAll();

    void delete(Long id);

    void delete(String username);
}
