package com.epam.gym.dao.impl;


import com.epam.gym.dao.AbstractDAO;
import com.epam.gym.dao.TraineeDAO;
import com.epam.gym.model.Trainee;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Slf4j
public class TraineeDAOImpl extends AbstractDAO<Trainee> implements TraineeDAO {
    public TraineeDAOImpl() {
        super(Trainee.class);
    }

    @Override
    public Optional<Trainee> findByUsername(String username) {
        try {
            String hql = "SELECT t FROM Trainee t WHERE t.user.username = :username";
            Trainee trainee = entityManager.createQuery(hql, Trainee.class)
                    .setParameter("username", username)
                    .getSingleResult();
            return Optional.ofNullable(trainee);
        } catch (Exception e) {
            log.error("TraineeDAOImpl:: Error finding Trainee by username {}: {}", username, e.getMessage());
            return Optional.empty();
        }
    }

}