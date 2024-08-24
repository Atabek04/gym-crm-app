package com.gymcrm.dao.impl;

import com.gymcrm.dao.TraineeDAO;
import com.gymcrm.model.Trainee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class TraineeDAOImpl implements TraineeDAO {
    private final Map<Integer, Trainee> traineeStorage;

    @Autowired
    public TraineeDAOImpl(Map<Integer, Trainee> traineeStorage) {
        this.traineeStorage = traineeStorage;
    }

    @Override
    public void save(Trainee trainee) {
        traineeStorage.put(trainee.getId(), trainee);
    }

    @Override
    public void update(Trainee trainee) {
        traineeStorage.put(trainee.getId(), trainee);
    }

    @Override
    public Optional<Trainee> findById(int id) {
        return Optional.of(traineeStorage.get(id));
    }

    @Override
    public List<Trainee> findAll() {
        return List.copyOf(traineeStorage.values());
    }

    @Override
    public void delete(int id) {
        traineeStorage.remove(id);
    }
}
