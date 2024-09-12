package com.epam.gym.cli;

import com.epam.gym.dto.TrainingRequest;
import com.epam.gym.dto.TrainingResponse;
import com.epam.gym.exception.ResourceNotFoundException;
import com.epam.gym.facade.GymFacade;
import com.epam.gym.model.TrainingType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import static com.epam.gym.cli.CLIHelper.*;

@Component
@Slf4j
@RequiredArgsConstructor
public class TrainingCLI {
    private static final Logger logger = LoggerFactory.getLogger("prompt-logger");
    private final GymFacade gymFacade;


    public void createTraining(Scanner scanner) {
        try {
            Long trainerId = readLong(scanner, "Enter Trainer ID: ");
            if (isValidTrainerId(trainerId)) {
                return;
            }
            Long traineeId = readLong(scanner, "Enter Trainee ID: ");
            if (isValidTraineeId(traineeId)) {
                return;
            }
            String trainingName = readString(scanner, "Enter Training Name: ");
            TrainingType trainingType = readEnum(scanner);
            ZonedDateTime trainingDate = readDateTime(scanner);
            Long trainingDuration = readLong(scanner, "Enter Training Duration (in minutes): ");

            TrainingRequest request = new TrainingRequest(traineeId, trainerId, trainingName, trainingType, trainingDate, trainingDuration);
            gymFacade.saveTraining(request);
            log.info("Training created successfully.");
            displaySeparator();
        } catch (ResourceNotFoundException ex) {
            log.error("Resource not found: {}", ex.getMessage());
        } catch (Exception ex) {
            log.error("An unexpected error occurred: {}", ex.getMessage());
        }
    }

    public void findTrainingById(Scanner scanner) {
        try {
            Long id = readLong(scanner, "Enter Training ID: ");
            Optional<TrainingResponse> trainingResponse = Optional.ofNullable(gymFacade.findTrainingById(id));
            trainingResponse.ifPresentOrElse(
                    response -> logger.info("Training found: {}\n", response),
                    () -> log.error("Training not found.")
            );
            displaySeparator();
        } catch (ResourceNotFoundException ex) {
            log.error("Resource not found: {}", ex.getMessage());
        } catch (Exception ex) {
            log.error("An unexpected error occurred: {}", ex.getMessage());
        }
    }

    public void listAllTrainings() {
        try {
            List<TrainingResponse> trainings = gymFacade.findAllTrainings();
            logger.info("All Trainings:\n");
            trainings.forEach(training -> logger.info("{}\n", training));
            displaySeparator();
        } catch (ResourceNotFoundException ex) {
            log.error("Resource not found: {}", ex.getMessage());
        } catch (Exception ex) {
            log.error("An unexpected error occurred: {}", ex.getMessage());
        }
    }

    public boolean isValidTrainerId(Long trainerId) {
        boolean exists = gymFacade.findAllTrainers()
                .stream()
                .anyMatch(trainer -> trainer.trainerId().equals(trainerId));

        if (!exists) {
            log.warn("Trainer with ID {} does not exist. Please enter a valid Trainer ID.", trainerId);
        }

        return !exists;
    }

    public boolean isValidTraineeId(Long traineeId) {
        boolean exists = gymFacade.findAllTrainees()
                .stream()
                .anyMatch(trainee -> trainee.traineeID().equals(traineeId));

        if (!exists) {
            log.warn("Trainee with ID {} does not exist. Please enter a valid Trainee ID.", traineeId);
        }

        return !exists;
    }

    public void listAllTrainingsByCriteria(Scanner scanner) {
        try {
            Long trainerId = readLongNull(scanner, "Enter Trainer ID (or press Enter to skip): ");
            Long traineeId = readLongNull(scanner, "Enter Trainee ID (or press Enter to skip): ");
            LocalDate startDate = readDateNull(scanner, "Enter Start Date (yyyy-MM-dd) (or press Enter to skip): ");
            LocalDate endDate = null;
            if (startDate != null) {
                endDate = readDateNull(scanner, "Enter End Date (yyyy-MM-dd) (or press Enter to skip): ");
            }
            TrainingType trainingType = readEnumNull(scanner);
            String sortBy = readSortBy(scanner);


            boolean ascending = readYesNo(scanner, "Sort Ascending? (Y/N): ");


            var trainings = gymFacade.findTrainingsByCriteria(
                    trainerId,
                    traineeId,
                    startDate,
                    endDate,
                    trainingType != null ? trainingType.getId() : null,
                    sortBy,
                    ascending
            );

            if (trainings.isEmpty()) {
                log.info("No trainings found with the specified criteria.");
            } else {
                log.info("Trainings found:");
                trainings.forEach(training -> logger.info("{}\n", training));
            }
            displaySeparator();
        } catch (ResourceNotFoundException ex) {
            log.error("Resource not found: {}", ex.getMessage());
        } catch (Exception ex) {
            log.error("An unexpected error occurred: {}", ex.getMessage());
        }
    }

}
