package com.gymcrm.service.impl;

import com.gymcrm.dao.TraineeDAO;
import com.gymcrm.model.Trainee;
import com.gymcrm.service.TraineeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TraineeServiceImpl implements TraineeService {
    private final TraineeDAO traineeDAO;

    public TraineeServiceImpl(TraineeDAO traineeDAO) {
        this.traineeDAO = traineeDAO;
    }

    @Override
    public void createTrainee(Trainee trainee) {
        traineeDAO.save(trainee);
    }

    @Override
    public void updateTrainee(Trainee trainee) {
        traineeDAO.update(trainee);
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
        traineeDAO.delete(id);
    }
}
