package com.gymcrm.cli;

import com.gymcrm.dto.TraineeRequest;
import com.gymcrm.dto.TraineeResponse;
import com.gymcrm.exception.GlobalExceptionHandler;
import com.gymcrm.exception.ResourceNotFoundException;
import com.gymcrm.facade.GymFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import static com.gymcrm.cli.CLIHelper.*;
import static com.gymcrm.cli.CLIHelper.displaySeparator;

@Component
public class TraineeCLI {
    private final GymFacade gymFacade;
    private static final Logger logger = LoggerFactory.getLogger(TraineeCLI.class);

    public TraineeCLI(GymFacade gymFacade) {
        this.gymFacade = gymFacade;
    }

    public void createTrainee(Scanner scanner) {
        try {
            int traineeId = readInt(scanner, "Enter Trainee ID: ");
            if (gymFacade.findAllTrainees().stream().anyMatch(trainee -> trainee.getTraineeID() == traineeId)) {
                logger.warn("Trainee with this ID already exists. Please enter a different ID.");
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
            var dateOfBirth = readDate(scanner);
            String address = readString(scanner, "Enter Address: ");

            TraineeRequest request = new TraineeRequest(traineeId, userId, address, firstName, lastName, dateOfBirth);
            gymFacade.saveTrainee(request);
            logger.info("Trainee created successfully.");
            displaySeparator();
        } catch (ResourceNotFoundException ex) {
            GlobalExceptionHandler.handleResourceNotFoundException(ex);
        } catch (Exception ex) {
            GlobalExceptionHandler.handleGeneralException(ex);
        }
    }

    public void updateTrainee(Scanner scanner) {
        try {
            int traineeId = readInt(scanner, "Enter Trainee ID: ");
            if (isValidTraineeId(traineeId)) {
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
            LocalDate dateOfBirth = readDate(scanner);
            String address = readString(scanner, "Enter Address: ");

            TraineeRequest request = new TraineeRequest(traineeId, userId, address, firstName, lastName, dateOfBirth);
            gymFacade.updateTrainee(request);
            logger.info("Trainee updated successfully.");
            displaySeparator();
        } catch (ResourceNotFoundException ex) {
            GlobalExceptionHandler.handleResourceNotFoundException(ex);
        } catch (Exception ex) {
            GlobalExceptionHandler.handleGeneralException(ex);
        }
    }

    public void findTraineeById(Scanner scanner) {
        try {
            int id = readInt(scanner, "Enter Trainee ID: ");
            Optional<TraineeResponse> traineeResponse = Optional.ofNullable(gymFacade.findTraineeById(id));
            traineeResponse.ifPresentOrElse(
                    response -> System.out.println("Trainee found: " + response),
                    () -> logger.error("Trainee not found.")
            );
            displaySeparator();
        } catch (ResourceNotFoundException ex) {
            GlobalExceptionHandler.handleResourceNotFoundException(ex);
        } catch (Exception ex) {
            GlobalExceptionHandler.handleGeneralException(ex);
        }
    }

    public void listAllTrainees() {
        try {
            List<TraineeResponse> trainees = gymFacade.findAllTrainees();
            System.out.println("All Trainees:");
            trainees.forEach(System.out::println);
            displaySeparator();
        } catch (ResourceNotFoundException ex) {
            GlobalExceptionHandler.handleResourceNotFoundException(ex);
        } catch (Exception ex) {
            GlobalExceptionHandler.handleGeneralException(ex);
        }
    }

    public void deleteTraineeById(Scanner scanner) {
        try {
            int id = readInt(scanner, "Enter Trainee ID to Delete: ");
            if (isValidTraineeId(id)) {
                return;
            }
            gymFacade.deleteTrainee(id);
            logger.info("Trainee deleted successfully.");
            displaySeparator();
        } catch (ResourceNotFoundException ex) {
            GlobalExceptionHandler.handleResourceNotFoundException(ex);
        } catch (Exception ex) {
            GlobalExceptionHandler.handleGeneralException(ex);
        }
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
