package com.gymcrm.config;

import com.gymcrm.model.Trainee;
import com.gymcrm.model.Trainer;
import com.gymcrm.model.Training;
import com.gymcrm.model.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Configuration
public class StorageConfig {

    @Bean
    public Map<Integer, Trainee> traineeStorage() {
        return new ConcurrentHashMap<>();
    }

    @Bean
    public Map<Integer, Trainer> trainerStorage() {
        return new ConcurrentHashMap<>();
    }

    @Bean
    public Map<Integer, Training> trainingStorage() {
        return new ConcurrentHashMap<>();
    }

    @Bean
    public Map<Integer, User> userStorage() {
        return new ConcurrentHashMap<>();
    }
}
