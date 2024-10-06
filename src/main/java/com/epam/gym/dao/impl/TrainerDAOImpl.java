package com.epam.gym.dao.impl;

import com.epam.gym.dao.AbstractDAO;
import com.epam.gym.dao.TrainerDAO;
import com.epam.gym.dto.TrainerTrainingFilterRequest;
import com.epam.gym.model.Trainee;
import com.epam.gym.model.Trainer;
import com.epam.gym.model.Training;
import com.epam.gym.model.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
@Slf4j
public class TrainerDAOImpl extends AbstractDAO<Trainer> implements TrainerDAO {

    @PersistenceContext
    private EntityManager entityManager;

    public TrainerDAOImpl() {
        super(Trainer.class);
    }

    @Override
    public List<Trainer> findAllFreeTrainers(String username) {
        try {
            String hql = "SELECT t FROM Trainer t " +
                    "WHERE t.id NOT IN (SELECT tr.trainer.id FROM Training tr) " +
                    "AND t.user.username <> :username";

            return entityManager.createQuery(hql, Trainer.class)
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
            Trainer trainer = entityManager.createQuery(hql, Trainer.class)
                    .setParameter("username", username)
                    .getSingleResult();
            return Optional.ofNullable(trainer);
        } catch (Exception e) {
            log.error("Error finding Trainer by username {}: {}", username, e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public List<Trainee> getAssignedTrainees(String username) {
        String query = "SELECT t.trainee FROM Training t WHERE t.trainer.user.username = :username";
        return entityManager.createQuery(query, Trainee.class)
                .setParameter("username", username)
                .getResultList();
    }

    @Override
    public List<Training> findTrainerTrainingsByFilters(String username, TrainerTrainingFilterRequest filterRequest) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Training> query = cb.createQuery(Training.class);
        Root<Training> trainingRoot = query.from(Training.class);

        Join<Training, Trainer> trainerJoin = trainingRoot.join("trainer");
        Join<Trainer, User> userJoin = trainerJoin.join("user");

        List<Predicate> predicates = new ArrayList<>();

        predicates.add(cb.equal(userJoin.get("username"), username));
        if (filterRequest.getPeriodFrom() != null) {
            predicates.add(cb.greaterThanOrEqualTo(trainingRoot.get("trainingDate"), filterRequest.getPeriodFrom()));
        }
        if (filterRequest.getPeriodTo() != null) {
            predicates.add(cb.lessThanOrEqualTo(trainingRoot.get("trainingDate"), filterRequest.getPeriodTo()));
        }
        if (filterRequest.getTraineeName() != null && !filterRequest.getTraineeName().isEmpty()) {
            Join<Training, Trainee> traineeJoin = trainingRoot.join("trainee");
            Join<Trainee, User> traineeUserJoin = traineeJoin.join("user");
            predicates.add(cb.like(cb.lower(traineeUserJoin.get("username")), "%" + filterRequest.getTraineeName().toLowerCase() + "%"));
        }

        query.select(trainingRoot).where(predicates.toArray(new Predicate[0]));
        return entityManager.createQuery(query).getResultList();
    }
}