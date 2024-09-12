package com.epam.gym.dao;

import com.epam.gym.model.Training;

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
}
