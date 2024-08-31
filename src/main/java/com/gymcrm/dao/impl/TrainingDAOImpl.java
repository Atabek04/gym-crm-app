package com.gymcrm.dao.impl;

import com.gymcrm.dao.AbstractDAO;
import com.gymcrm.dao.TrainingDAO;
import com.gymcrm.model.Trainer;
import com.gymcrm.model.Training;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class TrainingDAOImpl extends AbstractDAO<Training> implements TrainingDAO {
    @Autowired
    public TrainingDAOImpl(Map<Integer, Training> trainingStorage) {
        super(trainingStorage, LoggerFactory.getLogger(TrainingDAOImpl.class), "Training");
    }

    @Override
    public void save(Training training) {
        super.save(training, training.getId());
    }

    @Override
    public Optional<Training> findById(int id) {
        return super.findById(id);
    }

    @Override
    public List<Training> findAll() {
        return super.findAll();
    }
}
