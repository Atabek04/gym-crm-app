package com.epam.gym.cli;

import com.epam.gym.dto.TrainerRequest;
import com.epam.gym.dto.TrainerResponse;
import com.epam.gym.exception.ResourceNotFoundException;
import com.epam.gym.facade.GymFacade;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import static com.epam.gym.cli.CLIHelper.*;

@Component
@Slf4j
public class TrainerCLI {
    private final GymFacade gymFacade;

    public TrainerCLI(GymFacade gymFacade) {
        this.gymFacade = gymFacade;
    }

    public void createTrainer(Scanner scanner) {
        try {
            Long trainerId = readLong(scanner, "Enter Trainer ID: ");
            if (gymFacade.findAllTrainers().stream().anyMatch(trainer -> trainer.trainerId().equals(trainerId))) {
                log.warn("Trainer with this ID already exists. Please enter a different ID.");
                displaySeparator();
                return;
            }

            Long userId = readLong(scanner, "Enter User ID: ");
            if (gymFacade.findAllUsers().stream().anyMatch(user -> user.getId().equals(userId))) {
                log.warn("User with this ID already exists. Please enter a different ID.");
                displaySeparator();
                return;
            }

            String firstName = readString(scanner, "Enter First Name: ");
            String lastName = readString(scanner, "Enter Last Name: ");
            var specialization = readEnum(scanner);

            TrainerRequest request = new TrainerRequest(trainerId, userId, firstName, lastName, specialization.toString());
            gymFacade.saveTrainer(request);
            log.info("Trainer created successfully.");
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
            Long userId = readLong(scanner, "Enter User ID: ");
            if (gymFacade.findAllUsers().stream().anyMatch(user -> user.getId().equals(userId))) {
                log.warn("User with this ID already exists. Please enter a different ID.");
                displaySeparator();
                return;
            }

            String firstName = readString(scanner, "Enter First Name: ");
            String lastName = readString(scanner, "Enter Last Name: ");
            String specialization = readString(scanner, "Enter Specialization: ");

            TrainerRequest request = new TrainerRequest(trainerId, userId, firstName, lastName, specialization);
            gymFacade.updateTrainer(request);
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
                    response -> System.out.println("Trainer found: " + response),
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
            System.out.println("All Trainers:");
            trainers.forEach(System.out::println);
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
