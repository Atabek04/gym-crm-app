package com.gymcrm.dao.impl;

import com.gymcrm.dao.TrainingDAO;
import com.gymcrm.model.Training;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class TrainingDAOImpl implements TrainingDAO {
    private static final Logger logger = LoggerFactory.getLogger(TrainingDAOImpl.class);
    private final Map<Integer, Training> trainingStorage;

    @Autowired
    public TrainingDAOImpl(Map<Integer, Training> trainingStorage) {
        this.trainingStorage = trainingStorage;
    }

    @Override
    public void save(Training training) {
        trainingStorage.put(training.getId(), training);
        logger.info("Training with ID {} has been saved", training.getId());
    }

    @Override
    public Optional<Training> findById(int id) {
        var training = trainingStorage.get(id);
        if (training == null) {
            logger.info("Training with ID {} has not been found", id);
            return Optional.empty();
        }
        logger.info("Training with ID {} has been found", id);
        return Optional.of(training);
    }

    @Override
    public List<Training> findAll() {
        var trainings = List.copyOf(trainingStorage.values());
        if (trainings.isEmpty()) {
            logger.info("No trainings have been found");
        } else {
            logger.info("All trainings have been found");
        }
        return trainings;
    }
}
