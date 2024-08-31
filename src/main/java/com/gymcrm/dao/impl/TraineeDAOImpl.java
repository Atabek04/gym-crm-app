package com.gymcrm.dao.impl;


import com.gymcrm.dao.AbstractDAO;
import com.gymcrm.dao.TraineeDAO;
import com.gymcrm.model.Trainee;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class TraineeDAOImpl extends AbstractDAO<Trainee> implements TraineeDAO {

    @Autowired
    public TraineeDAOImpl(Map<Integer, Trainee> traineeStorage) {
        super(traineeStorage, LoggerFactory.getLogger(TraineeDAOImpl.class), "Trainee");
    }

    @Override
    public void save(Trainee trainee) {
        super.save(trainee, trainee.getId());
    }

    @Override
    public void update(Trainee trainee) {
        super.update(trainee, trainee.getId());
    }

    @Override
    public Optional<Trainee> findById(int id) {
        return super.findById(id);
    }

    @Override
    public List<Trainee> findAll() {
        return super.findAll();
    }

    @Override
    public void delete(int id) {
        super.delete(id);
    }
}