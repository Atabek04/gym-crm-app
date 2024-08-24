package com.gymcrm.dao.impl;

import com.gymcrm.dao.TrainerDAO;
import com.gymcrm.model.Trainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class TrainerDAOImpl implements TrainerDAO {
    private final Map<Integer, Trainer> trainerStorage;

    @Autowired
    public TrainerDAOImpl(Map<Integer, Trainer> trainerStorage) {
        this.trainerStorage = trainerStorage;
    }

    @Override
    public void save(Trainer trainer) {
        trainerStorage.put(trainer.getId(), trainer);
    }

    @Override
    public void update(Trainer trainer) {
        trainerStorage.put(trainer.getId(), trainer);
    }

    @Override
    public Optional<Trainer> findById(int id) {
        return Optional.ofNullable(trainerStorage.get(id));
    }

    @Override
    public List<Trainer> findAll() {
        return List.copyOf(trainerStorage.values());
    }

    @Override
    public void delete(int id) {
        trainerStorage.remove(id);
    }
}
