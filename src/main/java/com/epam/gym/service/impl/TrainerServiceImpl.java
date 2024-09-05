package com.epam.gym.service.impl;

import com.epam.gym.dao.TrainerDAO;
import com.epam.gym.model.Trainer;
import com.epam.gym.service.AbstractService;
import com.epam.gym.service.TrainerService;
import org.springframework.stereotype.Service;

@Service
public class TrainerServiceImpl extends AbstractService<Trainer> implements TrainerService {
    public TrainerServiceImpl(TrainerDAO trainerDAO) {
        super(trainerDAO);
    }
}