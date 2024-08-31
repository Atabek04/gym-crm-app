package com.gymcrm.dao;

import com.gymcrm.model.Trainer;

import java.util.List;
import java.util.Optional;

public interface TrainerDAO {
    void save(Trainer trainer);

    void update(Trainer trainer);

    Optional<Trainer> findById(int id);

    List<Trainer> findAll();
}
