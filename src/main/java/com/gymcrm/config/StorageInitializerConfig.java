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


    public StorageInitializerConfig(Map<Integer, Trainee> traineeStorage, Map<Integer, Trainer> trainerStorage,
                                    Map<Integer, Training> trainingStorage, Map<Integer, User> userStorage) {
        this.traineeStorage = traineeStorage;
        this.trainerStorage = trainerStorage;
        this.trainingStorage = trainingStorage;
        this.userStorage = userStorage;
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
    }

    @PostConstruct
    public void initializeStorage() {
        loadDataFromFile(traineeDataFile, traineeStorage, new TypeReference<>() {}, "trainee");
        loadDataFromFile(trainerDataFile, trainerStorage, new TypeReference<>() {}, "trainer");
        loadDataFromFile(trainingDataFile, trainingStorage, new TypeReference<>() {}, "training");
        loadDataFromFile(userDataFile, userStorage, new TypeReference<>() {}, "user");
    }

    private <T> void loadDataFromFile(String filePath, Map<Integer, T> storage,
                                      TypeReference<List<T>> typeReference, String dataType) {
        logger.info("Starting to load {} data from file: {}", dataType, filePath);
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(filePath)) {
            if (inputStream == null) {
                logger.error("File not found: {}", filePath);
                return;
            }
            List<T> dataList = objectMapper.readValue(inputStream, typeReference);
            dataList.forEach(data -> storage.put(getId(data), data));
            logger.info("Successfully loaded {} data from file.", dataType);
        } catch (JsonParseException e) {
            logger.error("JSON parsing error while loading {} data from file: {}", dataType, filePath, e);
        } catch (JsonMappingException e) {
            logger.error("JSON mapping error while loading {} data from file: {}", dataType, filePath, e);
        } catch (IOException e) {
            logger.error("I/O error while loading {} data from file: {}", dataType, filePath, e);
        } catch (Exception e) {
            logger.error("Unexpected error while loading {} data from file: {}", dataType, filePath, e);
        }
    }

    private <T> Integer getId(T data) {
        if (data instanceof Trainee) return ((Trainee) data).getId();
        if (data instanceof Trainer) return ((Trainer) data).getId();
        if (data instanceof Training) return ((Training) data).getId();
        if (data instanceof User) return ((User) data).getId();
        throw new IllegalArgumentException("Unknown data type: " + data.getClass());
    }
}