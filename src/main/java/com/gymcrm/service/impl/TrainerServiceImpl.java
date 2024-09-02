package com.gymcrm.service.impl;

import com.gymcrm.dao.TrainerDAO;
import com.gymcrm.model.Trainer;
import com.gymcrm.service.AbstractService;
import com.gymcrm.service.TrainerService;
import org.springframework.stereotype.Service;

@Service
public class TrainerServiceImpl extends AbstractService<Trainer> implements TrainerService {
    public TrainerServiceImpl(TrainerDAO trainerDAO) {
        super(trainerDAO);
    }
}