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

import static com.epam.gym.cli.CLIHelper.readEnum;
import static com.epam.gym.cli.CLIHelper.readString;
import static com.epam.gym.cli.CLIHelper.displaySeparator;
import static com.epam.gym.cli.CLIHelper.readLong;
import static com.epam.gym.cli.CLIHelper.readDateTime;
import static com.epam.gym.cli.CLIHelper.readYesNo;
import static com.epam.gym.cli.CLIHelper.readLongNull;
import static com.epam.gym.cli.CLIHelper.readDateNull;
import static com.epam.gym.cli.CLIHelper.readEnumNull;
import static com.epam.gym.cli.CLIHelper.readSortBy;

/**
 * @deprecated As of version 1.1.0, replaced by controllers and a REST-based application.
 * Please use the respective REST endpoints to perform these operations.
 * @since 1.1.0
 */
@Deprecated(since = "1.1.0")
@Component
@Slf4j
@RequiredArgsConstructor
public class TrainingCLI {
    private static final Logger logger = LoggerFactory.getLogger("prompt-logger");
    private final GymFacade gymFacade;



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
