package com.gymcrm.config;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.gymcrm.model.Trainee;
import com.gymcrm.model.Trainer;
import com.gymcrm.model.Training;
import com.gymcrm.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

@Configuration
@PropertySource("classpath:application.properties")
public class StorageInitializerConfig {
    private static final Logger logger = LoggerFactory.getLogger(StorageInitializerConfig.class);

    @Value("${trainee.data.file}")
    private String traineeDataFile;

    @Value("${trainer.data.file}")
    private String trainerDataFile;

    @Value("${training.data.file}")
    private String trainingDataFile;

    @Value("${user.data.file}")
    private String userDataFile;

    private final Map<Integer, Trainee> traineeStorage;
    private final Map<Integer, Trainer> trainerStorage;
    private final Map<Integer, Training> trainingStorage;
    private final Map<Integer, User> userStorage;

    private final ObjectMapper objectMapper;

    @Autowired
    public StorageInitializerConfig(Map<Integer, Trainee> traineeStorage, Map<Integer, Trainer> trainerStorage, Map<Integer, Training> trainingStorage, Map<Integer, User> userStorage) {
        this.traineeStorage = traineeStorage;
        this.trainerStorage = trainerStorage;
        this.trainingStorage = trainingStorage;
        this.userStorage = userStorage;
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
    }

    @PostConstruct
    public void initializeStorage() {
        loadTrainersFromFile();
        loadTraineesFromFile();
        loadTrainingsFromFile();
        loadUsersFromFile();
    }

    private void loadTraineesFromFile() {
        logger.info("Starting to load trainee data from file: {}", traineeDataFile);
        try (var inputStream = getClass().getClassLoader().getResourceAsStream(traineeDataFile)) {
            if (inputStream == null) {
                throw new IllegalArgumentException("File not found: " + traineeDataFile);
            }
            List<Trainee> trainees = objectMapper.readValue(inputStream, new TypeReference<>() {
            });
            trainees.forEach(trainee -> traineeStorage.put(trainee.getId(), trainee));
            logger.info("Successfully loaded trainee data from file.");
        } catch (Exception e) {
            logger.error("Error loading trainee data from file: {}", traineeDataFile, e);
        }
    }

    private void loadTrainersFromFile() {
        logger.info("Starting to load trainer data from file: {}", trainerDataFile);
        try (var inputStream = getClass().getClassLoader().getResourceAsStream(trainerDataFile)) {
            if (inputStream == null) {
                throw new IllegalArgumentException("File not found: " + trainerDataFile);
            }
            List<Trainer> trainers = objectMapper.readValue(inputStream, new TypeReference<>() {
            });
            trainers.forEach(trainer -> trainerStorage.put(trainer.getId(), trainer));
            logger.info("Successfully loaded trainer data from file.");
        } catch (Exception e) {
            logger.error("Error loading trainer data from file: {}", trainerDataFile, e);
        }
    }

    private void loadTrainingsFromFile() {
        logger.info("Starting to load training data from file: {}", trainingDataFile);
        try (var inputStream = getClass().getClassLoader().getResourceAsStream(trainingDataFile)) {
            if (inputStream == null) {
                throw new IllegalArgumentException("File not found: " + trainingDataFile);
            }
            List<Training> trainings = objectMapper.readValue(inputStream, new TypeReference<>() {
            });
            trainings.forEach(training -> trainingStorage.put(training.getId(), training));
            logger.info("Successfully loaded training data from file.");
        } catch (Exception e) {
            logger.error("Error loading training data from file: {}", trainingDataFile, e);
        }
    }

    private void loadUsersFromFile() {
        logger.info("Starting to load user data from file: {}", userDataFile);
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(userDataFile)) {
            if (inputStream == null) {
                throw new IllegalArgumentException("File not found: " + userDataFile);
            }
            List<User> users = objectMapper.readValue(inputStream, new TypeReference<>() {
            });
            users.forEach(user -> userStorage.put(user.getId(), user));
            logger.info("Successfully loaded user data from file.");
        } catch (JsonParseException e) {
            logger.error("JSON parsing error while loading user data from file: {}", userDataFile, e);
        } catch (JsonMappingException e) {
            logger.error("JSON mapping error while loading user data from file: {}", userDataFile, e);
        } catch (IOException e) {
            logger.error("I/O error while loading user data from file: {}", userDataFile, e);
        } catch (Exception e) {
            logger.error("Unexpected error while loading user data from file: {}", userDataFile, e);
        }
    }
}