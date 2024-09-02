package com.gymcrm.dao.impl;

import com.gymcrm.dao.AbstractDAO;
import com.gymcrm.dao.TrainerDAO;
import com.gymcrm.model.Trainer;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class TrainerDAOImpl extends AbstractDAO<Trainer> implements TrainerDAO {

    @Autowired
    public TrainerDAOImpl(Map<Integer, Trainer> trainerStorage) {
        super(trainerStorage, LoggerFactory.getLogger(TrainerDAOImpl.class), "Trainer");
    }
}