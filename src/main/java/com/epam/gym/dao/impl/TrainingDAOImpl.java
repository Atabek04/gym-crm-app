package com.epam.gym.dao.impl;

import com.epam.gym.dao.AbstractDAO;
import com.epam.gym.dao.TrainingDAO;
import com.epam.gym.dto.TraineeTrainingFilterRequest;
import com.epam.gym.model.Training;
import com.epam.gym.model.TrainingTypeEntity;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class TrainingDAOImpl extends AbstractDAO<Training> implements TrainingDAO {
    public TrainingDAOImpl(SessionFactory sessionFactory) {
        super(Training.class, sessionFactory);
    }

    @Override
    public List<Training> findTrainingsByCriteria(Long trainerId,
                                                  Long traineeId,
                                                  LocalDate startDate,
                                                  LocalDate endDate,
                                                  Integer trainingTypeId,
                                                  String sortBy,
                                                  boolean ascending
    ) {
        CriteriaBuilder cb = getCurrentSession().getCriteriaBuilder();
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

        TypedQuery<Training> query = getCurrentSession().createQuery(cq);

        return query.getResultList();
    }

    @Override
    public List<Training> findTrainingsByFilters(Long traineeId, TraineeTrainingFilterRequest filterRequest) {
        var session = sessionFactory.getCurrentSession();
        var criteriaBuilder = session.getCriteriaBuilder();
        var criteriaQuery = criteriaBuilder.createQuery(Training.class);
        var root = criteriaQuery.from(Training.class);
        var predicates = new ArrayList<Predicate>();

        predicates.add(criteriaBuilder.equal(root.get("trainee").get("id"), traineeId));
        if (filterRequest.getPeriodFrom() != null) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("trainingDate"), filterRequest.getPeriodFrom()));
        }
        if (filterRequest.getPeriodTo() != null) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("trainingDate"), filterRequest.getPeriodTo()));
        }
        if (filterRequest.getTrainerName() != null) {
            predicates.add(criteriaBuilder.equal(root.get("trainer").get("user").get("username"), filterRequest.getTrainerName()));
        }
        if (filterRequest.getTrainingType() != null) {
            predicates.add(criteriaBuilder.equal(root.get("trainingTypeId"), filterRequest.getTrainingType().getId()));
        }

        criteriaQuery.where(criteriaBuilder.and(predicates.toArray(new Predicate[0])));
        var query = session.createQuery(criteriaQuery);
        return query.getResultList();
    }

    @Override
    public List<TrainingTypeEntity> getAllTrainingTypes() {
        String queryStr = "SELECT new TrainingTypeEntity(t.id, t.trainingTypeName) FROM TrainingTypeEntity t";
        return getCurrentSession().createQuery(queryStr, TrainingTypeEntity.class).getResultList();
    }

    @Override
    public List<Training> findByTraineeUsername(String username) {
        var session = getCurrentSession();
        String hql = "FROM Training t WHERE t.trainee.user.username = :username";
        return session.createQuery(hql, Training.class)
                .setParameter("username", username)
                .getResultList();
    }

    @Override
    public void deleteAll(List<Training> trainings) {
        var session = getCurrentSession();
        for (Training training : trainings) {
            session.remove(training);
        }
    }
}
