package com.gymcrm.service.impl;

import com.gymcrm.dao.TrainingDAO;
import com.gymcrm.model.Training;
import com.gymcrm.service.TrainingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainingServiceImpl implements TrainingService {
    private final TrainingDAO trainingDAO;

    @Autowired
    public TrainingServiceImpl(TrainingDAO trainingDAO) {
        this.trainingDAO = trainingDAO;
    }

    @Override
    public void createTraining(Training training) {
        trainingDAO.save(training);
    }

    @Override
    public void updateTraining(Training training) {
        trainingDAO.update(training);
    }

    @Override
    public Training getTraining(int id) {
        return trainingDAO.findById(id).orElse(null);
    }

    @Override
    public List<Training> getAllTrainings() {
        return trainingDAO.findAll();
    }

    @Override
    public void deleteTraining(int id) {
        trainingDAO.delete(id);
    }
}
