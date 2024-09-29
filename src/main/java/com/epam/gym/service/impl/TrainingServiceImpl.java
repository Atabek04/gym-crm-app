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
    private final TrainerDAO trainerDAO;
    private final TraineeDAO traineeDAO;

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

    @Override
    public void create(TrainingRequest request) {
        var trainer = trainerDAO.findByUsername(request.trainerUsername())
                        .orElseThrow(() -> new ResourceNotFoundException("Trainer not found"));
        var trainee = traineeDAO.findByUsername(request.traineeUsername())
                .orElseThrow(() -> new ResourceNotFoundException("Trainee not found"));
        trainingDAO.save(TrainingMapper.toTraining(request, trainee, trainer));
    }

    @Override
    public List<TrainingTypeResponse> getAllTrainingTypes() {
        return trainingDAO.getAllTrainingTypes()
                .stream()
                .map(tr -> TrainingTypeResponse.builder()
                        .trainingTypeId(tr.getId().intValue())
                        .trainingType(TrainingType.valueOf(tr.getTrainingTypeName()))
                        .build()).toList();
    }
}