package com.gymcrm.cli;

import com.gymcrm.dto.TrainingRequest;
import com.gymcrm.dto.TrainingResponse;
import com.gymcrm.exception.GlobalExceptionHandler;
import com.gymcrm.exception.ResourceNotFoundException;
import com.gymcrm.facade.GymFacade;
import com.gymcrm.model.TrainingType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import static com.gymcrm.cli.CLIHelper.*;
import static com.gymcrm.cli.CLIHelper.displaySeparator;

@Component
public class TrainingCLI {
    private final GymFacade gymFacade;
    private final static Logger logger = LoggerFactory.getLogger(TrainingCLI.class);

    public TrainingCLI(GymFacade gymFacade) {
        this.gymFacade = gymFacade;
    }

    public void createTraining(Scanner scanner) {
        try {
            int id = readInt(scanner, "Enter Training ID: ");
            if (gymFacade.findAllTrainings().stream().anyMatch(training -> training.getId() == id)) {
                logger.warn("Training with this ID already exists. Please enter a different ID.");
                displaySeparator();
                return;
            }

            int trainerId = readInt(scanner, "Enter Trainer ID: ");
            if (isValidTrainerId(trainerId)) {
                return;
            }
            int traineeId = readInt(scanner, "Enter Trainee ID: ");
            if (isValidTraineeId(traineeId)) {
                return;
            }
            String trainingName = readString(scanner, "Enter Training Name: ");
            TrainingType trainingType = readEnum(scanner);
            LocalDateTime trainingDate = readDateTime(scanner);
            long trainingDuration = readLong(scanner, "Enter Training Duration (in minutes): ");

            TrainingRequest request = new TrainingRequest(id, traineeId, trainerId, trainingName, trainingType, trainingDate, trainingDuration);
            gymFacade.saveTraining(request);
            logger.info("Training created successfully.");
            displaySeparator();
        } catch (ResourceNotFoundException ex) {
            GlobalExceptionHandler.handleResourceNotFoundException(ex);
        } catch (Exception ex) {
            GlobalExceptionHandler.handleGeneralException(ex);
        }
    }

    public void findTrainingById(Scanner scanner) {
        try {
            int id = readInt(scanner, "Enter Training ID: ");
            Optional<TrainingResponse> trainingResponse = Optional.ofNullable(gymFacade.findTrainingById(id));
            trainingResponse.ifPresentOrElse(
                    response -> System.out.println("Training found: " + response),
                    () -> logger.error("Training not found.")
            );
            displaySeparator();
        } catch (ResourceNotFoundException ex) {
            GlobalExceptionHandler.handleResourceNotFoundException(ex);
        } catch (Exception ex) {
            GlobalExceptionHandler.handleGeneralException(ex);
        }
    }

    public void listAllTrainings() {
        try {
            List<TrainingResponse> trainings = gymFacade.findAllTrainings();
            System.out.println("All Trainings:");
            trainings.forEach(System.out::println);
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

    public boolean isValidTraineeId(int traineeId) {
        boolean exists = gymFacade.findAllTrainees()
                .stream()
                .anyMatch(trainee -> trainee.getTraineeID() == traineeId);

        if (!exists) {
            logger.warn("Trainee with ID {} does not exist. Please enter a valid Trainee ID.", traineeId);
        }

        return !exists;
    }
}
