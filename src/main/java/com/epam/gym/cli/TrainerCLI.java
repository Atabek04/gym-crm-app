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

import static com.epam.gym.cli.CLIHelper.*;

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

    public void updateTrainer(Scanner scanner) {
        try {
            Long trainerId = readLong(scanner, "Enter Trainer ID to Update: ");
            if (isValidTrainerId(trainerId)) {
                return;
            }

            String firstName = readString(scanner, "Enter First Name: ");
            String lastName = readString(scanner, "Enter Last Name: ");
            var specialization = readEnum(scanner);

            TrainerRequest request = new TrainerRequest(firstName, lastName, specialization.toString());
            gymFacade.updateTrainer(request, trainerId);
            log.info("Trainer updated successfully.");
            displaySeparator();
        } catch (ResourceNotFoundException ex) {
            log.error("Resource not found: {}", ex.getMessage());
        } catch (Exception ex) {
            log.error("An unexpected error occurred: {}", ex.getMessage());
        }
    }

    public void findTrainerById(Scanner scanner) {
        try {
            Long id = readLong(scanner, "Enter Trainer ID: ");
            if (isValidTrainerId(id)) {
                return;
            }
            Optional<TrainerResponse> trainerResponse = Optional.ofNullable(gymFacade.findTrainerById(id));
            trainerResponse.ifPresentOrElse(
                    response -> logger.info("Trainer found: {}\n", response),
                    () -> log.error("Trainer not found.")
            );
            displaySeparator();
        } catch (ResourceNotFoundException ex) {
            log.error("Resource not found: {}", ex.getMessage());
        } catch (Exception ex) {
            log.error("An unexpected error occurred: {}", ex.getMessage());
        }
    }

    public void listAllTrainers() {
        try {
            List<TrainerResponse> trainers = gymFacade.findAllTrainers();
            logger.info("All Trainers:\n");
            trainers.forEach(trainer -> logger.info("{}\n", trainer));
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
}
