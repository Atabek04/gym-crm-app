package com.gymcrm.dao.impl;

import com.gymcrm.dao.AbstractDAO;
import com.gymcrm.dao.TrainerDAO;
import com.gymcrm.model.Trainer;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class TrainerDAOImpl extends AbstractDAO<Trainer> implements TrainerDAO {

    @Autowired
    public TrainerDAOImpl(Map<Integer, Trainer> trainerStorage) {
        super(trainerStorage, LoggerFactory.getLogger(TrainerDAOImpl.class), "Trainer");
    }

    @Override
    public void save(Trainer trainer) {
        super.save(trainer, trainer.getId());
    }

    @Override
    public void update(Trainer trainer) {
        super.update(trainer, trainer.getId());
    }

    @Override
    public Optional<Trainer> findById(int id) {
        return super.findById(id);
    }

    @Override
    public List<Trainer> findAll() {
        return super.findAll();
    }
}