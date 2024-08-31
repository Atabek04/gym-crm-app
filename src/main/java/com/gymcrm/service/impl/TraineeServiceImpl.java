package com.gymcrm.service.impl;

import com.gymcrm.dao.TraineeDAO;
import com.gymcrm.model.Trainee;
import com.gymcrm.service.TraineeService;
import com.gymcrm.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TraineeServiceImpl implements TraineeService {
    private static final Logger logger = LoggerFactory.getLogger(TraineeServiceImpl.class);
    private final TraineeDAO traineeDAO;

    public TraineeServiceImpl(TraineeDAO traineeDAO) {
        this.traineeDAO = traineeDAO;
    }

    @Override
    public void createTrainee(Trainee trainee) {
        traineeDAO.save(trainee);
        logger.info("Trainee with ID {} created successfully", trainee.getId());
    }

    @Override
    public void updateTrainee(Trainee trainee) {
        if (!traineeDAO.findById(trainee.getId()).isPresent()) {
            throw new ResourceNotFoundException("Trainee with ID " + trainee.getId() + " not found");
        }
        traineeDAO.update(trainee);
        logger.info("Trainee with ID {} updated successfully", trainee.getId());
    }

    @Override
    public Optional<Trainee> getTrainee(int id) {
        return traineeDAO.findById(id);
    }

    @Override
    public List<Trainee> getAllTrainees() {
        return traineeDAO.findAll();
    }

    @Override
    public void deleteTrainee(int id) {
        if (!traineeDAO.findById(id).isPresent()) {
            throw new ResourceNotFoundException("Trainee with ID " + id + " not found");
        }
        traineeDAO.delete(id);
        logger.info("Trainee with ID {} deleted successfully", id);
    }
}