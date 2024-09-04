package com.gymcrm.cli;

import com.gymcrm.dto.TrainerRequest;
import com.gymcrm.dto.TrainerResponse;
import com.gymcrm.exception.GlobalExceptionHandler;
import com.gymcrm.exception.ResourceNotFoundException;
import com.gymcrm.facade.GymFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import static com.gymcrm.cli.CLIHelper.*;
import static com.gymcrm.cli.CLIHelper.displaySeparator;

@Component
public class TrainerCLI {
    private final GymFacade gymFacade;
    private final static Logger logger = LoggerFactory.getLogger(TrainerCLI.class);

    public TrainerCLI(GymFacade gymFacade) {
        this.gymFacade = gymFacade;
    }

    public void createTrainer(Scanner scanner) {
        try {
            int trainerId = readInt(scanner, "Enter Trainer ID: ");
            if (gymFacade.findAllTrainers().stream().anyMatch(trainer -> trainer.getTrainerId() == trainerId)) {
                logger.warn("Trainer with this ID already exists. Please enter a different ID.");
                displaySeparator();
                return;
            }

            int userId = readInt(scanner, "Enter User ID: ");
            if (gymFacade.findAllUsers().stream().anyMatch(user -> user.getId() == userId)) {
                logger.warn("User with this ID already exists. Please enter a different ID.");
                displaySeparator();
                return;
            }

            String firstName = readString(scanner, "Enter First Name: ");
            String lastName = readString(scanner, "Enter Last Name: ");
            var specialization = readEnum(scanner);

            TrainerRequest request = new TrainerRequest(trainerId, userId, firstName, lastName, specialization.toString());
            gymFacade.saveTrainer(request);
            logger.info("Trainer created successfully.");
            displaySeparator();
        } catch (ResourceNotFoundException ex) {
            GlobalExceptionHandler.handleResourceNotFoundException(ex);
        } catch (Exception ex) {
            GlobalExceptionHandler.handleGeneralException(ex);
        }
    }

    public void updateTrainer(Scanner scanner) {
        try {
            int trainerId = readInt(scanner, "Enter Trainer ID to Update: ");
            if (isValidTrainerId(trainerId)) {
                return;
            }
            int userId = readInt(scanner, "Enter User ID: ");
            if (gymFacade.findAllUsers().stream().anyMatch(user -> user.getId() == userId)) {
                logger.warn("User with this ID already exists. Please enter a different ID.");
                displaySeparator();
                return;
            }

            String firstName = readString(scanner, "Enter First Name: ");
            String lastName = readString(scanner, "Enter Last Name: ");
            String specialization = readString(scanner, "Enter Specialization: ");

            TrainerRequest request = new TrainerRequest(trainerId, userId, firstName, lastName, specialization);
            gymFacade.updateTrainer(request);
            logger.info("Trainer updated successfully.");
            displaySeparator();
        } catch (ResourceNotFoundException ex) {
            GlobalExceptionHandler.handleResourceNotFoundException(ex);
        } catch (Exception ex) {
            GlobalExceptionHandler.handleGeneralException(ex);
        }
    }

    public void findTrainerById(Scanner scanner) {
        try {
            int id = readInt(scanner, "Enter Trainer ID: ");
            if (isValidTrainerId(id)) {
                return;
            }
            Optional<TrainerResponse> trainerResponse = Optional.ofNullable(gymFacade.findTrainerById(id));
            trainerResponse.ifPresentOrElse(
                    response -> System.out.println("Trainer found: " + response),
                    () -> logger.error("Trainer not found.")
            );
            displaySeparator();
        } catch (ResourceNotFoundException ex) {
            GlobalExceptionHandler.handleResourceNotFoundException(ex);
        } catch (Exception ex) {
            GlobalExceptionHandler.handleGeneralException(ex);
        }
    }

    public void listAllTrainers() {
        try {
            List<TrainerResponse> trainers = gymFacade.findAllTrainers();
            System.out.println("All Trainers:");
            trainers.forEach(System.out::println);
            displaySeparator();
        } catch (ResourceNotFoundException ex) {
            GlobalExceptionHandler.handleResourceNotFoundException(ex);
        } catch (Exception ex) {
            GlobalExceptionHandler.handleGeneralException(ex);
        }
    }

    public boolean isValidTrainerId(int trainerId) {
        boolean exists = gymFacade.findAllTrainers()
                .stream()
                .anyMatch(trainer -> trainer.getTrainerId() == trainerId);

        if (!exists) {
            logger.warn("Trainer with ID {} does not exist. Please enter a valid Trainer ID.", trainerId);
        }

        return !exists;
    }
}
