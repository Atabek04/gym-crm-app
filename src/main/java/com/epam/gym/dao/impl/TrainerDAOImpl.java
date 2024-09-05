package com.epam.gym.dao.impl;

import com.epam.gym.dao.AbstractDAO;
import com.epam.gym.dao.TrainerDAO;
import com.epam.gym.model.Trainer;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class TrainerDAOImpl extends AbstractDAO<Trainer> implements TrainerDAO {

    public TrainerDAOImpl(Map<Long, Trainer> trainerStorage) {
        super(trainerStorage, LoggerFactory.getLogger(TrainerDAOImpl.class), "Trainer");
    }
}