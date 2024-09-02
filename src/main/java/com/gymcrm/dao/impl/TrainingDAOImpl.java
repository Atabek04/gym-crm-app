package com.gymcrm.dao.impl;

import com.gymcrm.dao.AbstractDAO;
import com.gymcrm.dao.TrainingDAO;
import com.gymcrm.model.Training;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class TrainingDAOImpl extends AbstractDAO<Training> implements TrainingDAO {
    @Autowired
    public TrainingDAOImpl(Map<Integer, Training> trainingStorage) {
        super(trainingStorage, LoggerFactory.getLogger(TrainingDAOImpl.class), "Training");
    }
}
