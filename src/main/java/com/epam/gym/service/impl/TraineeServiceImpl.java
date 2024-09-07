package com.epam.gym.service.impl;

import com.epam.gym.dao.TraineeDAO;
import com.epam.gym.model.Trainee;
import com.epam.gym.service.TraineeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TraineeServiceImpl implements TraineeService {
    private final TraineeDAO traineeDAO;

    @Override
    public void create(Trainee trainee, Long id) {
        traineeDAO.save(trainee, id);
    }

    @Override
    public void update(Trainee trainee, Long id) {
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
}