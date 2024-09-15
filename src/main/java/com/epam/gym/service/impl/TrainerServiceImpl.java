package com.epam.gym.service.impl;

import com.epam.gym.dao.TrainerDAO;
import com.epam.gym.exception.ResourceNotFoundException;
import com.epam.gym.model.Trainer;
import com.epam.gym.service.TrainerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class TrainerServiceImpl implements TrainerService {
    private final TrainerDAO trainerDAO;

    @Override
    public Optional<Trainer> create(Trainer trainer) {
        log.debug("Creating trainee with ID: {}", trainer.getId());
        return trainerDAO.save(trainer);
    }

    @Override
    public void update(Trainer trainer, Long id) {
        var oldTrainer = findById(id);
        if (oldTrainer.isEmpty()) {
            throw new ResourceNotFoundException("Trainer not found");
        }
        trainerDAO.update(trainer, id);
    }

    @Override
    public Optional<Trainer> findById(Long id) {
        return trainerDAO.findById(id);
    }

    @Override
    public List<Trainer> findAll() {
        return trainerDAO.findAll();
    }

    @Override
    public void delete(Long id) {
        trainerDAO.delete(id);
    }

    @Override
    public List<Trainer> findAllFreeTrainers(String username) {
        return trainerDAO.findAllFreeTrainers(username);
    }
}