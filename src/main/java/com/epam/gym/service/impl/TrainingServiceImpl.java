package com.epam.gym.service.impl;

import com.epam.gym.dao.TrainingDAO;
import com.epam.gym.exception.ResourceNotFoundException;
import com.epam.gym.model.Training;
import com.epam.gym.service.TrainingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class TrainingServiceImpl implements TrainingService {

    private final TrainingDAO trainingDAO;

    @Override
    public void create(Training training) {
        trainingDAO.save(training);
    }

    @Override
    public void update(Training training, Long id) {
        var oldTraining = trainingDAO.findById(id);
        if (oldTraining.isEmpty()) {
            throw new ResourceNotFoundException("Training not found");
        }
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
    public List<Training> findTrainingsByCriteria(Long trainerId,
                                                  Long traineeId,
                                                  LocalDate startDate,
                                                  LocalDate endDate,
                                                  Integer typeId,
                                                  String sortBy,
                                                  boolean ascending) {
        return trainingDAO.findTrainingsByCriteria(trainerId, traineeId, startDate, endDate, typeId, sortBy, ascending);
    }

    @Override
    public void delete(Long id) {
        trainingDAO.delete(id);
    }
}