package com.epam.gym.dao.impl;


import com.epam.gym.dao.AbstractDAO;
import com.epam.gym.dao.TraineeDAO;
import com.epam.gym.model.Trainee;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class TraineeDAOImpl extends AbstractDAO<Trainee> implements TraineeDAO {

    public TraineeDAOImpl(Map<Long, Trainee> traineeStorage) {
        super(traineeStorage, LoggerFactory.getLogger(TraineeDAOImpl.class), "Trainee");
    }
}