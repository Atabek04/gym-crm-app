package com.epam.gym.cli;

import com.epam.gym.dto.TraineeRequest;
import com.epam.gym.dto.TraineeResponse;
import com.epam.gym.exception.ResourceNotFoundException;
import com.epam.gym.facade.GymFacade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import static com.epam.gym.cli.CLIHelper.readString;
import static com.epam.gym.cli.CLIHelper.displaySeparator;
import static com.epam.gym.cli.CLIHelper.readLong;
import static com.epam.gym.cli.CLIHelper.readDate;

/**
 * @deprecated As of version 1.1.0, replaced by controllers and a REST-based application.
 * Please use the respective REST endpoints to perform these operations.
 * @since 1.1.0
 */
@Deprecated(since = "1.1.0")
@Component
@Slf4j
@RequiredArgsConstructor
public class TraineeCLI {
    private static final Logger logger = LoggerFactory.getLogger("prompt-logger");
    private final GymFacade gymFacade;

    public void createTrainee(Scanner scanner) {
        try {
            String firstName = readString(scanner, "Enter First Name: ");
            String lastName = readString(scanner, "Enter Last Name: ");
            var dateOfBirth = readDate(scanner);
            String address = readString(scanner, "Enter Address: ");

            TraineeRequest request = new TraineeRequest(address, firstName, lastName, dateOfBirth);
            var newTrainee = gymFacade.saveTrainee(request)
                    .orElseThrow(() -> new ResourceNotFoundException("Trainee not returned"));
            displaySeparator();
            log.info("Trainee created successfully.");
            logger.info("Your username: {}\n", newTrainee.getUser().getUsername());
            logger.info("Your temporary password: {}\n", newTrainee.getUser().getPassword());
            displaySeparator();
        } catch (ResourceNotFoundException ex) {
            log.error("Resource not found: {}", ex.getMessage());
        } catch (Exception ex) {
            log.error("An unexpected error occurred: {}", ex.getMessage());
        }
    }


    public void findTraineeById(Scanner scanner) {
        try {
            Long id = readLong(scanner, "Enter Trainee ID: ");
            Optional<TraineeResponse> traineeResponse = Optional.ofNullable(gymFacade.findTraineeById(id));
            traineeResponse.ifPresentOrElse(
                    response -> logger.info("Trainee found: {}\n", response),
                    () -> log.error("Trainee not found.")
            );
            displaySeparator();
        } catch (ResourceNotFoundException ex) {
            log.error("Resource not found: {}", ex.getMessage());
        } catch (Exception ex) {
            log.error("An unexpected error occurred: {}", ex.getMessage());
        }
    }

    public void listAllTrainees() {
        try {
            List<TraineeResponse> trainees = gymFacade.findAllTrainees();
            logger.info("All Trainees:\n");
            trainees.forEach(trainee -> logger.info("{}\n", trainee));
            displaySeparator();
        } catch (ResourceNotFoundException ex) {
            log.error("Resource not found: {}", ex.getMessage());
        } catch (Exception ex) {
            log.error("An unexpected error occurred: {}", ex.getMessage());
        }
    }

}
