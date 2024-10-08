package com.epam.gym.dao.impl;


import com.epam.gym.dao.AbstractDAO;
import com.epam.gym.dao.TraineeDAO;
import com.epam.gym.model.Trainee;
import com.epam.gym.model.Trainer;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Slf4j
public class TraineeDAOImpl extends AbstractDAO<Trainee> implements TraineeDAO {
    public TraineeDAOImpl(SessionFactory sessionFactory) {
        super(Trainee.class, sessionFactory);
    }

    @Override
    public Optional<Trainee> findByUsername(String username) {
        try {
            String hql = "SELECT t FROM Trainee t WHERE t.user.username = :username";
            Trainee trainee = sessionFactory.getCurrentSession()
                    .createQuery(hql, Trainee.class)
                    .setParameter("username", username)
                    .getSingleResult();
            return Optional.ofNullable(trainee);
        } catch (Exception e) {
            log.error("TraineeDAOImpl:: Error finding Trainee by username {}: {}", username, e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public List<Optional<Trainer>> getAssignedTrainers(String username) {
        try {
            Optional<Trainee> traineeOptional = findByUsername(username);
            if (traineeOptional.isEmpty()) {
                log.error("TraineeDAOImpl:: No trainee found with username {}", username);
                return List.of();
            }
            Trainee trainee = traineeOptional.get();

            String hql = "SELECT tr.trainer FROM Training tr WHERE tr.trainee.id = :traineeId";
            return sessionFactory.getCurrentSession()
                    .createQuery(hql, Trainer.class)
                    .setParameter("traineeId", trainee.getId())
                    .getResultList()
                    .stream()
                    .map(Optional::ofNullable)
                    .toList();
        } catch (Exception e) {
            log.error("TraineeDAOImpl:: Error retrieving trainers for trainee {}: {}", username, e.getMessage());
            return List.of();
        }
    }

    @Override
    public void delete(String username) {
        try {
            String hql = "from Trainee t where t.user.username = :username";
            var trainee = sessionFactory.getCurrentSession()
                    .createQuery(hql, Trainee.class)
                    .setParameter("username", username)
                    .uniqueResult();

            if (trainee != null) {
                sessionFactory.getCurrentSession().remove(trainee);
                log.info("Trainee and associated user with username {} have been deleted", username);
            } else {
                log.warn("Trainee with username {} not found", username);
            }
        } catch (Exception e) {
            log.error("TraineeDAOImpl:: Error deleting trainee with username {}: {}", username, e.getMessage());
            throw e;
        }
    }

    @Override
    public List<Trainer> getNotAssignedTrainers(String username) {
        String hql = """
                    SELECT t FROM Trainer t
                    WHERE t.id NOT IN (
                        SELECT tr.trainer.id\s
                        FROM Training tr\s
                        WHERE tr.trainee.user.username = :username
                    )
                """;

        return getCurrentSession().createQuery(hql, Trainer.class)
                .setParameter("username", username)
                .getResultList();
    }

}