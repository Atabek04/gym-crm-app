package com.epam.gym.service.impl;

import com.epam.gym.dao.TraineeDAO;
import com.epam.gym.exception.ResourceNotFoundException;
import com.epam.gym.model.Trainee;
import com.epam.gym.model.Trainer;
import com.epam.gym.service.TraineeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class TraineeServiceImpl implements TraineeService {
    private final TraineeDAO traineeDAO;

    @Override
    public Optional<Trainee> create(Trainee trainee) {
        log.debug("Creating trainee with ID: {}", trainee.getId());
        return traineeDAO.save(trainee);
    }

    @Override
    public void update(Trainee trainee, Long id) {
        var oldTrainee = findById(id);
        if (oldTrainee.isEmpty()) {
            throw new ResourceNotFoundException("Trainee not found");
        }
        traineeDAO.update(trainee, id);
    }

    @Override
    public Optional<Trainee> findById(Long id) {
        return traineeDAO.findById(id);
    }

    @Override
    public List<Trainee> findAll() {
        return traineeDAO.findAll();
    }

    @Override
    public void delete(Long id) {
        traineeDAO.delete(id);
    }

    @Override
    public Optional<Trainee> findByUsername(String username) {
        return traineeDAO.findByUsername(username);
    }

    @Override
    public List<Optional<Trainer>> getAssignedTrainers(String username) {
        return traineeDAO.getAssignedTrainers(username);
    }

}