package com.epam.gym.dao.impl;

import com.epam.gym.dao.AbstractDAO;
import com.epam.gym.dao.TraineeDAO;
import com.epam.gym.model.Trainee;
import com.epam.gym.model.Trainer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
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
            return Optional.ofNullable(entityManager.createQuery(hql, Trainee.class)
                    .setParameter("username", username)
                    .getSingleResult());
        } catch (Exception e) {
            log.error("Error finding Trainee by username {}: {}", username, e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public List<Optional<Trainer>> getAssignedTrainers(String username) {
        try {
            Optional<Trainee> traineeOptional = findByUsername(username);
            if (traineeOptional.isEmpty()) {
                log.error("No trainee found with username {}", username);
                return List.of();
            }
            String hql = "SELECT tr.trainer FROM Training tr WHERE tr.trainee.user.username = :username";
            return entityManager.createQuery(hql, Trainer.class)
                    .setParameter("username", username)
                    .getResultList()
                    .stream()
                    .map(Optional::ofNullable)
                    .toList();
        } catch (Exception e) {
            log.error("Error retrieving trainers for trainee {}: {}", username, e.getMessage());
            return List.of();
        }
    }

    @Override
    public void delete(String username) {
        try {
            String hql = "FROM Trainee t WHERE t.user.username = :username";
            var trainee = entityManager.createQuery(hql, Trainee.class)
                    .setParameter("username", username)
                    .getSingleResult();

            if (trainee != null) {
                entityManager.remove(trainee);
                log.info("Trainee and associated user with username {} have been deleted", username);
            } else {
                log.warn("Trainee with username {} not found", username);
            }
        } catch (Exception e) {
            log.error("Error deleting trainee with username {}: {}", username, e.getMessage());
        }
    }

    @Override
    public List<Trainer> getNotAssignedTrainers(String username) {
        String hql = """
                    SELECT t FROM Trainer t
                    WHERE t.id NOT IN (
                        SELECT tr.trainer.id
                        FROM Training tr
                        WHERE tr.trainee.user.username = :username
                    )
                """;
        return entityManager.createQuery(hql, Trainer.class)
                .setParameter("username", username)
                .getResultList();
    }
}