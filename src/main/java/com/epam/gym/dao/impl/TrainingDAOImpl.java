package com.epam.gym.dao.impl;

import com.epam.gym.dao.AbstractDAO;
import com.epam.gym.dao.TrainingDAO;
import com.epam.gym.model.Training;
import org.springframework.stereotype.Repository;

@Repository
public class TrainingDAOImpl extends AbstractDAO<Training> implements TrainingDAO {
    public TrainingDAOImpl() {
        super(Training.class);
    }
}
