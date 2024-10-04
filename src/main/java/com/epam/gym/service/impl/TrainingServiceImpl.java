package com.epam.gym.service.impl;

import com.epam.gym.dao.TraineeDAO;
import com.epam.gym.dao.TrainerDAO;
import com.epam.gym.dao.TrainingDAO;
import com.epam.gym.dto.TrainingRequest;
import com.epam.gym.dto.TrainingTypeResponse;
import com.epam.gym.exception.ResourceNotFoundException;
import com.epam.gym.mapper.TrainingMapper;
import com.epam.gym.model.Training;
import com.epam.gym.model.TrainingType;
import com.epam.gym.service.TrainingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class TrainingServiceImpl implements TrainingService {

    private final TrainingDAO trainingDAO;
    private final TrainerDAO trainerDAO;
    private final TraineeDAO traineeDAO;

    @Override
    public void create(Training training) {
        log.info("Creating training for trainee: {} and trainer: {}", training.getTrainee().getId(), training.getTrainer().getId());
        trainingDAO.save(training);
        log.info("Training created successfully.");
    }

    @Override
    public void update(Training training, Long id) {
        log.info("Updating training with ID: {}", id);
        trainingDAO.update(training, id);
        log.info("Training with ID: {} updated successfully.", id);
    }

    @Override
    public Optional<Training> findById(Long id) {
        log.info("Fetching training by ID: {}", id);
        return trainingDAO.findById(id);
    }

    @Override
    public List<Training> findAll() {
        log.info("Fetching all trainings.");
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
        log.info("Fetching trainings by criteria.");
        return trainingDAO.findTrainingsByCriteria(trainerId, traineeId, startDate, endDate, typeId, sortBy, ascending);
    }

    @Override
    public void delete(Long id) {
        log.info("Deleting training with ID: {}", id);
        trainingDAO.delete(id);
        log.info("Training with ID: {} deleted successfully.", id);
    }

    @Override
    public void create(TrainingRequest request) {
        log.info("Creating training for request with trainer: {} and trainee: {}", request.trainerUsername(), request.traineeUsername());
        var trainer = trainerDAO.findByUsername(request.trainerUsername())
                .orElseThrow(() -> new ResourceNotFoundException("Trainer not found"));
        var trainee = traineeDAO.findByUsername(request.traineeUsername())
                .orElseThrow(() -> new ResourceNotFoundException("Trainee not found"));
        trainingDAO.save(TrainingMapper.toTraining(request, trainee, trainer));
        log.info("Training created successfully for trainee: {} and trainer: {}", request.traineeUsername(), request.trainerUsername());
    }

    @Override
    public List<TrainingTypeResponse> getAllTrainingTypes() {
        log.info("Fetching all training types.");
        return trainingDAO.getAllTrainingTypes()
                .stream()
                .map(tr -> TrainingTypeResponse.builder()
                        .trainingTypeId(tr.getId().intValue())
                        .trainingType(TrainingType.valueOf(tr.getTrainingTypeName()))
                        .build()).toList();
    }
}
