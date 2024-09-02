package com.gymcrm.service.impl;

import com.gymcrm.dao.TrainingDAO;
import com.gymcrm.model.Training;
import com.gymcrm.service.AbstractService;
import com.gymcrm.service.TrainingService;
import org.springframework.stereotype.Service;

@Service
public class TrainingServiceImpl extends AbstractService<Training> implements TrainingService {
    public TrainingServiceImpl(TrainingDAO trainingDAO) {
        super(trainingDAO);
    }
}