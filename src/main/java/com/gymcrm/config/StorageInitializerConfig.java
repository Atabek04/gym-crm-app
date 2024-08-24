package com.gymcrm.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.gymcrm.model.Trainee;
import com.gymcrm.model.Trainer;
import com.gymcrm.model.Training;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.annotation.PostConstruct;
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

    private final Map<Integer, Trainee> traineeStorage;
    private final Map<Integer, Trainer> trainerStorage;
    private final Map<Integer, Training> trainingStorage;

    private final ObjectMapper objectMapper;

    @Autowired
    public StorageInitializerConfig(Map<Integer, Trainee> traineeStorage,
                                    Map<Integer, Trainer> trainerStorage,
                                    Map<Integer, Training> trainingStorage) {
        this.traineeStorage = traineeStorage;
        this.trainerStorage = trainerStorage;
        this.trainingStorage = trainingStorage;
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
        System.out.println("Constructor of StorageInitializerConfig");
    }

    @PostConstruct
    public void initializeStorage() {
        loadTraineesFromFile();
        loadTrainersFromFile();
        loadTrainingsFromFile();
        System.out.println("Loading of data is finished :: PostConstruct");
    }

    private void loadTraineesFromFile() {
        logger.info("Starting to load trainee data from file: {}", traineeDataFile);
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(traineeDataFile)) {
            if (inputStream == null) {
                throw new IllegalArgumentException("File not found: " + traineeDataFile);
            }
            List<Trainee> trainees = objectMapper.readValue(inputStream, new TypeReference<>() {
            });
            trainees.forEach(trainee -> traineeStorage.put(trainee.getId(), trainee));
            logger.info("Successfully loaded trainee data from file.");
        } catch (Exception e) {
            logger.error("Error loading trainee data from file: {}", traineeDataFile, e);
            throw new RuntimeException("Error loading trainee data from file: " + traineeDataFile, e);
        }
    }

    private void loadTrainersFromFile() {
        System.out.println("Starting to load trainer from file: " + trainerDataFile);
        logger.info("Starting to load trainer data from file: {}", trainerDataFile);
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(trainerDataFile)) {
            if (inputStream == null) {
                throw new IllegalArgumentException("File not found: " + trainerDataFile);
            }
            List<Trainer> trainers = objectMapper.readValue(inputStream, new TypeReference<List<Trainer>>() {});
            trainers.forEach(trainer -> trainerStorage.put(trainer.getId(), trainer));
            System.out.println("Successfully loaded trainer data from file");
            logger.info("Successfully loaded trainer data from file.");
        } catch (Exception e) {
            logger.error("Error loading trainer data from file: {}", trainerDataFile, e);
        }
    }

    private void loadTrainingsFromFile() {
        logger.info("Starting to load training data from file: {}", trainingDataFile);
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(trainingDataFile)) {
            if (inputStream == null) {
                throw new IllegalArgumentException("File not found: " + trainingDataFile);
            }
            List<Training> trainings = objectMapper.readValue(inputStream, new TypeReference<List<Training>>() {});
            trainings.forEach(training -> trainingStorage.put(training.getId(), training));
            logger.info("Successfully loaded training data from file.");
        } catch (Exception e) {
            logger.error("Error loading training data from file: {}", trainingDataFile, e);
        }
    }
}