package com.gymcrm.config;

import com.gymcrm.model.Trainee;
import com.gymcrm.model.Trainer;
import com.gymcrm.model.Training;
import com.gymcrm.model.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class StorageConfig {

    @Bean
    public Map<Integer, Trainee> traineeStorage() {
        return new HashMap<>();
    }

    @Bean
    public Map<Integer, Trainer> trainerStorage() {
        return new HashMap<>();
    }

    @Bean
    public Map<Integer, Training> trainingStorage() {
        return new HashMap<>();
    }

    @Bean Map<Integer, User> userStorage() {
        return new HashMap<>();
    }
}
