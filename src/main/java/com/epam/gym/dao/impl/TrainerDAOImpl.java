package com.epam.gym.dao.impl;

import com.epam.gym.dao.AbstractDAO;
import com.epam.gym.dao.TrainerDAO;
import com.epam.gym.model.Trainer;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
@Slf4j
public class TrainerDAOImpl extends AbstractDAO<Trainer> implements TrainerDAO {
    public TrainerDAOImpl(SessionFactory sessionFactory) {
        super(Trainer.class, sessionFactory);
    }

    @Override
    public List<Trainer> findAllFreeTrainers(String username) {
        try {
            String hql = "SELECT t FROM Trainer t " +
                    "WHERE t.id NOT IN (SELECT tr.trainer.id FROM Training tr) " +
                    "AND t.user.username <> :username";

            return getCurrentSession().createQuery(hql, Trainer.class)
                    .setParameter("username", username)
                    .getResultList();
        } catch (Exception e) {
            log.error("Error finding free trainers: {}", e.getMessage());
            return Collections.emptyList();
        }
    }

    @Override
    public Optional<Trainer> findByUsername(String username) {
        try {
            String hql = "SELECT t FROM Trainer t WHERE t.user.username = :username";
            Trainer trainer = getCurrentSession().createQuery(hql, Trainer.class)
                    .setParameter("username", username)
                    .getSingleResult();
            return Optional.ofNullable(trainer);
        } catch (Exception e) {
            log.error("TrainerDAOImpl:: Error finding Trainer by username {}: {}", username, e.getMessage());
            return Optional.empty();
        }
    }
}