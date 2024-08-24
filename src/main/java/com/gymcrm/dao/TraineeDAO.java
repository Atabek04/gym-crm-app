package com.gymcrm.dao;

import com.gymcrm.model.Trainee;

import java.util.List;
import java.util.Optional;

public interface TraineeDAO {
    void save(Trainee trainee);

    void update(Trainee trainee);

    Optional<Trainee> findById(int id);

    List<Trainee> findAll();

    void delete(int id);
}
