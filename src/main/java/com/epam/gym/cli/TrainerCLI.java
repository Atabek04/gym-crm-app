package com.epam.gym.cli;

import com.epam.gym.dto.TrainerRequest;
import com.epam.gym.dto.TrainerResponse;
import com.epam.gym.exception.ResourceNotFoundException;
import com.epam.gym.facade.GymFacade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import static com.epam.gym.cli.CLIHelper.readEnum;
import static com.epam.gym.cli.CLIHelper.readString;
import static com.epam.gym.cli.CLIHelper.displaySeparator;
import static com.epam.gym.cli.CLIHelper.readLong;

/**
 * @deprecated As of version 1.1.0, replaced by controllers and a REST-based application.
 * Please use the respective REST endpoints to perform these operations.
 * @since 1.1.0
 */
@Deprecated(since = "1.1.0")
@Component
@Slf4j
@RequiredArgsConstructor
public class TrainerCLI {
    private static final Logger logger = LoggerFactory.getLogger("prompt-logger");
    private final GymFacade gymFacade;

    public void createTrainer(Scanner scanner) {
        try {
            String firstName = readString(scanner, "Enter First Name: ");
            String lastName = readString(scanner, "Enter Last Name: ");
            var specialization = readEnum(scanner);

            TrainerRequest request = new TrainerRequest(firstName, lastName, specialization.toString());
            var newTrainer = gymFacade.saveTrainer(request)
                    .orElseThrow(() -> new ResourceNotFoundException("Trainer not returned"));
            displaySeparator();
            logger.info("Trainer created successfully.\n");
            logger.info("Your username: {}\n", newTrainer.getUser().getUsername());
            logger.info("Your temporary password: {}\n", newTrainer.getUser().getPassword());
            displaySeparator();
        } catch (ResourceNotFoundException ex) {
            log.error("Resource not found: {}", ex.getMessage());
        } catch (Exception ex) {
            log.error("An unexpected error occurred: {}", ex.getMessage());
        }
    }

    public void listAllFreeTrainers(String username) {
        try {
            List<TrainerResponse> trainers = gymFacade.listAllFreeTrainers(username);
            logger.info("All Free Trainers:\n");
            trainers.forEach(trainer -> logger.info("{}\n", trainer));
            displaySeparator();
        } catch (ResourceNotFoundException ex) {
            log.error("Resource not found: {}", ex.getMessage());
        } catch (Exception ex) {
            log.error("An unexpected error occurred: {}", ex.getMessage());
        }
    }
}
