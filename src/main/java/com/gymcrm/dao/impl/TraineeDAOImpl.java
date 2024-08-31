package com.gymcrm.dao.impl;

import com.gymcrm.dao.TraineeDAO;
import com.gymcrm.model.Trainee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class TraineeDAOImpl implements TraineeDAO {
    private static final Logger logger = LoggerFactory.getLogger(TraineeDAOImpl.class);

    private final Map<Integer, Trainee> traineeStorage;

    @Autowired
    public TraineeDAOImpl(Map<Integer, Trainee> traineeStorage) {
        this.traineeStorage = traineeStorage;
    }

    @Override
    public void save(Trainee trainee) {
        traineeStorage.put(trainee.getId(), trainee);
        logger.info("Trainee with ID {} has been saved", trainee.getId());
    }

    @Override
    public void update(Trainee trainee) {
        traineeStorage.put(trainee.getId(), trainee);
        logger.info("Trainee with ID {} has been updated", trainee.getId());
    }

    @Override
    public Optional<Trainee> findById(int id) {
        var trainee = traineeStorage.get(id);
        if (trainee == null) {
            logger.info("Trainee with ID {} has not been found", id);
            return Optional.empty();
        }
        logger.info("Trainee with ID {} has been found", id);
        return Optional.of(trainee);
    }

    @Override
    public List<Trainee> findAll() {
        var trainees = List.copyOf(traineeStorage.values());
        if (trainees.isEmpty()) {
            logger.info("No trainees have been found");
        } else {
            logger.info("All trainees have been found");
        }
        return trainees;
    }

    @Override
    public void delete(int id) {
        traineeStorage.remove(id);
        logger.info("Trainee with ID {} has been deleted", id);
    }
}
