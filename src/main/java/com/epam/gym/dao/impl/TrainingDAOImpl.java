package com.epam.gym.dao.impl;

import com.epam.gym.dao.AbstractDAO;
import com.epam.gym.dao.TrainingDAO;
import com.epam.gym.model.Training;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class TrainingDAOImpl extends AbstractDAO<Training> implements TrainingDAO {
    @Autowired
    public TrainingDAOImpl(Map<Long, Training> trainingStorage) {
        super(trainingStorage, LoggerFactory.getLogger(TrainingDAOImpl.class), "Training");
    }
}
