package com.epam.gym.service.impl;

import com.epam.gym.dao.TrainingDAO;
import com.epam.gym.model.Training;
import com.epam.gym.service.AbstractService;
import com.epam.gym.service.TrainingService;
import org.springframework.stereotype.Service;

@Service
public class TrainingServiceImpl extends AbstractService<Training> implements TrainingService {
    public TrainingServiceImpl(TrainingDAO trainingDAO) {
        super(trainingDAO);
    }
}