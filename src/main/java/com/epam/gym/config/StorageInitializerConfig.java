package com.epam.gym.config;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.epam.gym.model.Trainee;
import com.epam.gym.model.Trainer;
import com.epam.gym.model.Training;
import com.epam.gym.model.User;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
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

    private final Map<Long, Trainee> traineeStorage;
    private final Map<Long, Trainer> trainerStorage;
    private final Map<Long, Training> trainingStorage;
    private final Map<Long, User> userStorage;
    private final ObjectMapper objectMapper = new ObjectMapper();


    @PostConstruct
    public void initializeStorage() {
        this.objectMapper.registerModule(new JavaTimeModule());
        loadDataFromFile(traineeDataFile, traineeStorage, new TypeReference<>() {}, "trainee");
        loadDataFromFile(trainerDataFile, trainerStorage, new TypeReference<>() {}, "trainer");
        loadDataFromFile(trainingDataFile, trainingStorage, new TypeReference<>() {}, "training");
        loadDataFromFile(userDataFile, userStorage, new TypeReference<>() {}, "user");
    }

    private <T> void loadDataFromFile(String filePath, Map<Long, T> storage,
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

    private <T> Long getId(T data) {
        if (data instanceof Trainee) {
            return ((Trainee) data).getId();
        }
        if (data instanceof Trainer) {
            return ((Trainer) data).getId();
        }
        if (data instanceof Training) {
            return ((Training) data).getId();
        }
        if (data instanceof User) {
            return ((User) data).getId();
        }
        throw new IllegalArgumentException("Unknown data type: " + data.getClass());
    }
}