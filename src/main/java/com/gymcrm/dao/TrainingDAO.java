package com.gymcrm.dao;

import com.gymcrm.model.Training;

import java.util.List;
import java.util.Optional;

public interface TrainingDAO {
    void save(Training training);

    Optional<Training> findById(int id);

    List<Training> findAll();
}
