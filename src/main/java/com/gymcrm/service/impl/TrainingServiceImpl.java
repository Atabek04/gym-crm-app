package com.gymcrm.service.impl;

import com.gymcrm.dao.TrainingDAO;
import com.gymcrm.exception.ResourceNotFoundException;
import com.gymcrm.model.Training;
import com.gymcrm.service.TrainingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainingServiceImpl implements TrainingService {
    private static final Logger logger = LoggerFactory.getLogger(TrainingServiceImpl.class);
    private final TrainingDAO trainingDAO;

    public TrainingServiceImpl(TrainingDAO trainingDAO) {
        this.trainingDAO = trainingDAO;
    }

    @Override
    public void createTraining(Training training) {
        trainingDAO.save(training);
        logger.info("Training with ID {} created successfully.", training.getId());
    }

    @Override
    public Training getTraining(int id) {
        return trainingDAO.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Training with ID " + id + " not found."));
    }

    @Override
    public List<Training> getAllTrainings() {
        return trainingDAO.findAll();
    }
}