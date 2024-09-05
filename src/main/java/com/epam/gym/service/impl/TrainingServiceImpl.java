package com.epam.gym.service.impl;

import com.epam.gym.dao.TrainingDAO;
import com.epam.gym.model.Training;
import com.epam.gym.service.TrainingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TrainingServiceImpl implements TrainingService {

    private final TrainingDAO trainingDAO;

    @Override
    public void create(Training training, Long id) {
        trainingDAO.save(training, id);
    }

    @Override
    public void update(Training training, Long id) {
        trainingDAO.update(training, id);
    }

    @Override
    public Optional<Training> findById(Long id) {
        return trainingDAO.findById(id);
    }

    @Override
    public List<Training> findAll() {
        return trainingDAO.findAll();
    }

    @Override
    public void delete(Long id) {
        trainingDAO.delete(id);
    }
}