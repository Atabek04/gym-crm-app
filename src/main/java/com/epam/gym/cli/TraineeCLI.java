package com.epam.gym.cli;

import com.epam.gym.dto.TraineeRequest;
import com.epam.gym.dto.TraineeResponse;
import com.epam.gym.exception.ResourceNotFoundException;
import com.epam.gym.facade.GymFacade;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import static com.epam.gym.cli.CLIHelper.*;

@Component
@Slf4j
public class TraineeCLI {
    private final GymFacade gymFacade;

    public TraineeCLI(GymFacade gymFacade) {
        this.gymFacade = gymFacade;
    }

    public void createTrainee(Scanner scanner) {
        try {
            Long traineeId = readLong(scanner, "Enter Trainee ID: ");
            if (gymFacade.findAllTrainees().stream().anyMatch(trainee -> trainee.traineeID().equals(traineeId))) {
                log.warn("Trainee with this ID already exists. Please enter a different ID.");
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
            var dateOfBirth = readDate(scanner);
            String address = readString(scanner, "Enter Address: ");

            TraineeRequest request = new TraineeRequest(traineeId, userId, address, firstName, lastName, dateOfBirth);
            gymFacade.saveTrainee(request);
            log.info("Trainee created successfully.");
            displaySeparator();
        } catch (ResourceNotFoundException ex) {
            log.error("Resource not found: {}", ex.getMessage());
        } catch (Exception ex) {
            log.error("An unexpected error occurred: {}", ex.getMessage());
        }
    }

    public void updateTrainee(Scanner scanner) {
        try {
            Long traineeId = readLong(scanner, "Enter Trainee ID: ");
            if (isValidTraineeId(traineeId)) {
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
            LocalDate dateOfBirth = readDate(scanner);
            String address = readString(scanner, "Enter Address: ");

            TraineeRequest request = new TraineeRequest(traineeId, userId, address, firstName, lastName, dateOfBirth);
            gymFacade.updateTrainee(request);
            log.info("Trainee updated successfully.");
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
                    response -> System.out.println("Trainee found: " + response),
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
            System.out.println("All Trainees:");
            trainees.forEach(System.out::println);
            displaySeparator();
        } catch (ResourceNotFoundException ex) {
            log.error("Resource not found: {}", ex.getMessage());
        } catch (Exception ex) {
            log.error("An unexpected error occurred: {}", ex.getMessage());
        }
    }

    public void deleteTraineeById(Scanner scanner) {
        try {
            Long id = readLong(scanner, "Enter Trainee ID to Delete: ");
            if (isValidTraineeId(id)) {
                return;
            }
            gymFacade.deleteTrainee(id);
            log.info("Trainee deleted successfully.");
            displaySeparator();
        } catch (ResourceNotFoundException ex) {
            log.error("Resource not found: {}", ex.getMessage());
        } catch (Exception ex) {
            log.error("An unexpected error occurred: {}", ex.getMessage());
        }
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
}
