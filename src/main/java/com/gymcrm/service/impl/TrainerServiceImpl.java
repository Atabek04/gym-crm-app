package com.gymcrm.service.impl;

import com.gymcrm.dao.TrainerDAO;
import com.gymcrm.exception.ResourceNotFoundException;
import com.gymcrm.model.Trainer;
import com.gymcrm.service.TrainerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TrainerServiceImpl implements TrainerService {
    private static final Logger logger = LoggerFactory.getLogger(TrainerServiceImpl.class);
    private final TrainerDAO trainerDAO;

    public TrainerServiceImpl(TrainerDAO trainerDAO) {
        this.trainerDAO = trainerDAO;
    }

    @Override
    public void createTrainer(Trainer trainer) {
        trainerDAO.save(trainer);
        logger.info("Trainer with ID {} created successfully.", trainer.getId());
    }

    @Override
    public void updateTrainer(Trainer trainer) {
        if (trainerDAO.findById(trainer.getId()).isEmpty()) {
            throw new ResourceNotFoundException("Trainer with ID " + trainer.getId() + " not found.");
        }
        trainerDAO.update(trainer);
        logger.info("Trainer with ID {} updated successfully.", trainer.getId());
    }

    @Override
    public Optional<Trainer> getTrainer(int id) {
        return Optional.ofNullable(trainerDAO.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Trainer with ID " + id + " not found.")));
    }

    @Override
    public List<Trainer> getAllTrainers() {
        return trainerDAO.findAll();
    }
}