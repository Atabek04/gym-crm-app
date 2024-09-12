package com.epam.gym.dao.impl;

import com.epam.gym.dao.AbstractDAO;
import com.epam.gym.dao.TrainerDAO;
import com.epam.gym.model.Trainer;
import org.springframework.stereotype.Repository;

@Repository
public class TrainerDAOImpl extends AbstractDAO<Trainer> implements TrainerDAO {
    public TrainerDAOImpl() {
        super(Trainer.class);
    }
}