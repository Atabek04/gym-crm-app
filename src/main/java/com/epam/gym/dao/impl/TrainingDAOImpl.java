package com.epam.gym.dao.impl;

import com.epam.gym.dao.AbstractDAO;
import com.epam.gym.dao.TrainingDAO;
import com.epam.gym.dto.TraineeTrainingFilterRequest;
import com.epam.gym.model.Training;
import com.epam.gym.model.TrainingTypeEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
@Slf4j
public class TrainingDAOImpl extends AbstractDAO<Training> implements TrainingDAO {

    @PersistenceContext
    private EntityManager entityManager;

    public TrainingDAOImpl() {
        super(Training.class);
    }

    @Override
    public List<Training> findTrainingsByCriteria(Long trainerId,
                                                  Long traineeId,
                                                  LocalDate startDate,
                                                  LocalDate endDate,
                                                  Integer trainingTypeId,
                                                  String sortBy,
                                                  boolean ascending) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Training> cq = cb.createQuery(Training.class);
        Root<Training> root = cq.from(Training.class);

        List<Predicate> predicates = new ArrayList<>();

        if (trainerId != null) {
            predicates.add(cb.equal(root.get("trainer").get("id"), trainerId));
        }
        if (traineeId != null) {
            predicates.add(cb.equal(root.get("trainee").get("id"), traineeId));
        }
        if (startDate != null && endDate != null) {
            predicates.add(cb.between(root.get("trainingDate"), startDate, endDate));
        }
        if (trainingTypeId != null) {
            predicates.add(cb.equal(root.get("trainingTypeId"), trainingTypeId));
        }

        cq.where(cb.and(predicates.toArray(new Predicate[0])));

        if (sortBy != null && !sortBy.isEmpty() && !sortBy.isBlank()) {
            if (ascending) {
                cq.orderBy(cb.asc(root.get(sortBy)));
            } else {
                cq.orderBy(cb.desc(root.get(sortBy)));
            }
        }

        TypedQuery<Training> query = entityManager.createQuery(cq);

        return query.getResultList();
    }

    @Override
    public List<Training> findTrainingsByFilters(Long traineeId, TraineeTrainingFilterRequest filterRequest) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Training> criteriaQuery = cb.createQuery(Training.class);
        Root<Training> root = criteriaQuery.from(Training.class);
        List<Predicate> predicates = new ArrayList<>();

        predicates.add(cb.equal(root.get("trainee").get("id"), traineeId));
        if (filterRequest.getPeriodFrom() != null) {
            predicates.add(cb.greaterThanOrEqualTo(root.get("trainingDate"), filterRequest.getPeriodFrom()));
        }
        if (filterRequest.getPeriodTo() != null) {
            predicates.add(cb.lessThanOrEqualTo(root.get("trainingDate"), filterRequest.getPeriodTo()));
        }
        if (filterRequest.getTrainerName() != null) {
            predicates.add(cb.equal(root.get("trainer").get("user").get("username"), filterRequest.getTrainerName()));
        }
        if (filterRequest.getTrainingType() != null) {
            predicates.add(cb.equal(root.get("trainingTypeId"), filterRequest.getTrainingType().getId()));
        }

        criteriaQuery.where(cb.and(predicates.toArray(new Predicate[0])));
        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    @Override
    public List<TrainingTypeEntity> getAllTrainingTypes() {
        String queryStr = "SELECT new TrainingTypeEntity(t.id, t.trainingTypeName) FROM TrainingTypeEntity t";
        return entityManager.createQuery(queryStr, TrainingTypeEntity.class).getResultList();
    }

    @Override
    public List<Training> findByTraineeUsername(String username) {
        String hql = "FROM Training t WHERE t.trainee.user.username = :username";
        return entityManager.createQuery(hql, Training.class)
                .setParameter("username", username)
                .getResultList();
    }

    @Override
    public void deleteAll(List<Training> trainings) {
        for (Training training : trainings) {
            entityManager.remove(training);
        }
    }
}