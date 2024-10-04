package com.epam.gym.dao;

import com.epam.gym.dto.TraineeTrainingFilterRequest;
import com.epam.gym.model.Training;
import com.epam.gym.model.TrainingTypeEntity;

import java.time.LocalDate;
import java.util.List;

public interface TrainingDAO extends BaseDAO<Training> {

    List<Training> findTrainingsByCriteria(Long trainerId,
                                           Long traineeId,
                                           LocalDate startDate,
                                           LocalDate endDate,
                                           Integer trainingTypeId,
                                           String sortBy,
                                           boolean ascending
    );

    List<Training> findTrainingsByFilters(Long id, TraineeTrainingFilterRequest filterRequest);

    List<TrainingTypeEntity> getAllTrainingTypes();

    List<Training> findByTraineeUsername(String username);

    void deleteAll(List<Training> trainings);
}
