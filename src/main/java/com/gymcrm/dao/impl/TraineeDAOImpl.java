package com.gymcrm.dao.impl;


import com.gymcrm.dao.AbstractDAO;
import com.gymcrm.dao.TraineeDAO;
import com.gymcrm.model.Trainee;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class TraineeDAOImpl extends AbstractDAO<Trainee> implements TraineeDAO {

    @Autowired
    public TraineeDAOImpl(Map<Integer, Trainee> traineeStorage) {
        super(traineeStorage, LoggerFactory.getLogger(TraineeDAOImpl.class), "Trainee");
    }
}