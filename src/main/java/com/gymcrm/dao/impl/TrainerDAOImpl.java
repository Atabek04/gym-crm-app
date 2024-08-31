package com.gymcrm.dao.impl;

import com.gymcrm.dao.TrainerDAO;
import com.gymcrm.model.Trainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class TrainerDAOImpl implements TrainerDAO {
    private static final Logger logger = LoggerFactory.getLogger(TrainerDAOImpl.class);

    private final Map<Integer, Trainer> trainerStorage;

    @Autowired
    public TrainerDAOImpl(Map<Integer, Trainer> trainerStorage) {
        this.trainerStorage = trainerStorage;
    }

    @Override
    public void save(Trainer trainer) {
        trainerStorage.put(trainer.getId(), trainer);
        logger.info("Trainer with ID {} has been saved", trainer.getId());
    }

    @Override
    public void update(Trainer trainer) {
        trainerStorage.put(trainer.getId(), trainer);
        logger.info("Trainer with ID {} has been updated", trainer.getId());
    }

    @Override
    public Optional<Trainer> findById(int id) {
        var trainer = trainerStorage.get(id);
        if (trainer == null) {
            logger.info("Trainer with ID {} has not been found", id);
            return Optional.empty();
        }
        logger.info("Trainer with ID {} has been found", id);
        return Optional.of(trainer);
    }

    @Override
    public List<Trainer> findAll() {
        var trainers = List.copyOf(trainerStorage.values());
        if (trainers.isEmpty()) {
            logger.info("No trainers have been found");
        } else {
            logger.info("All trainers have been found");
        }
        return trainers;
    }
}
