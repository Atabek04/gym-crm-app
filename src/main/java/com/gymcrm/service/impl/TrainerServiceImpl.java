package com.gymcrm.service.impl;

import com.gymcrm.dao.TrainerDAO;
import com.gymcrm.model.Trainer;
import com.gymcrm.service.TrainerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TrainerServiceImpl implements TrainerService {
    private final TrainerDAO trainerDAO;

    @Autowired
    public TrainerServiceImpl(TrainerDAO trainerDAO) {
        this.trainerDAO = trainerDAO;
    }

    @Override
    public void createTrainer(Trainer trainer) {
        trainerDAO.save(trainer);
    }

    @Override
    public void updateTrainer(Trainer trainer) {
        trainerDAO.update(trainer);
    }

    @Override
    public Trainer getTrainer(int id) {
        return trainerDAO.findById(id).orElse(null);
    }

    @Override
    public List<Trainer> getAllTrainer() {
        return trainerDAO.findAll();
    }

    @Override
    public void deleteTrainer(int id) {
        trainerDAO.delete(id);
    }
}
