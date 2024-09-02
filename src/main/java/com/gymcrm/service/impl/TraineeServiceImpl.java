package com.gymcrm.service.impl;

import com.gymcrm.dao.TraineeDAO;
import com.gymcrm.model.Trainee;
import com.gymcrm.service.AbstractService;
import com.gymcrm.service.TraineeService;
import org.springframework.stereotype.Service;

@Service
public class TraineeServiceImpl extends AbstractService<Trainee> implements TraineeService {
    public TraineeServiceImpl(TraineeDAO traineeDAO) {
        super(traineeDAO);
    }
}