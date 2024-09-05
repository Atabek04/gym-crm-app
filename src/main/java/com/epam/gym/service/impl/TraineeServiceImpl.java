package com.epam.gym.service.impl;

import com.epam.gym.dao.TraineeDAO;
import com.epam.gym.model.Trainee;
import com.epam.gym.service.AbstractService;
import com.epam.gym.service.TraineeService;
import org.springframework.stereotype.Service;

@Service
public class TraineeServiceImpl extends AbstractService<Trainee> implements TraineeService {
    public TraineeServiceImpl(TraineeDAO traineeDAO) {
        super(traineeDAO);
    }
}