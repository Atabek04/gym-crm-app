package com.gymcrm.dao.impl;

import com.gymcrm.dao.TrainingDAO;
import com.gymcrm.model.Training;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class TrainingDAOImpl implements TrainingDAO {
    private final Map<Integer, Training> trainingStorage;

    @Autowired
    public TrainingDAOImpl(Map<Integer, Training> trainingStorage) {
        this.trainingStorage = trainingStorage;
    }

    @Override
    public void save(Training training) {
        trainingStorage.put(training.getId(), training);
    }

    @Override
    public void update(Training training) {
        trainingStorage.put(training.getId(), training);
    }

    @Override
    public Optional<Training> findById(int id) {
        return Optional.of(trainingStorage.get(id));
    }

    @Override
    public List<Training> findAll() {
        return List.copyOf(trainingStorage.values());
    }

    @Override
    public void delete(int id) {
        trainingStorage.remove(id);
    }
}
