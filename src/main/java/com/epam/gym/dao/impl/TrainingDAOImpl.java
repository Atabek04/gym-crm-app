package com.epam.gym.dao.impl;

import com.epam.gym.dao.AbstractDAO;
import com.epam.gym.dao.TrainingDAO;
import com.epam.gym.model.Training;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class TrainingDAOImpl extends AbstractDAO<Training> implements TrainingDAO {
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
                                                  boolean ascending
    ) {
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
}
