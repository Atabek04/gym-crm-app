package com.gymcrm;

import com.gymcrm.dto.*;
import com.gymcrm.exception.GlobalExceptionHandler;
import com.gymcrm.exception.ResourceNotFoundException;
import com.gymcrm.facade.GymFacade;
import com.gymcrm.model.TrainingType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@Component
public class GymCLI {
    private final GymFacade gymFacade;

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    @Autowired
    public GymCLI(GymFacade gymFacade) {
        this.gymFacade = gymFacade;
    }
    private static final Logger logger = LoggerFactory.getLogger(GymCLI.class);

    public void start() {
        Scanner scanner = new Scanner(System.in);
        displayWelcomeMessage();
        boolean isRunning = true;

        while (isRunning) {
            displayMenu();
            try {
                int choice = readInt(scanner, "Choose an option: ");
                handleUserChoice(choice, scanner);
                if (choice == 13) {
                    isRunning = false;
                }
            } catch (Exception ex) {
                logger.error("An error occurred: ", ex);
            }
        }

        logger.info("Thank you for using the Gym CRM CLI. Goodbye!");
    }

    private void handleUserChoice(int choice, Scanner scanner) {
        switch (choice) {
            case 1 -> createTrainee(scanner);
            case 2 -> updateTrainee(scanner);
            case 3 -> findTraineeById(scanner);
            case 4 -> listAllTrainees();
            case 5 -> deleteTraineeById(scanner);
            case 6 -> createTrainer(scanner);
            case 7 -> updateTrainer(scanner);
            case 8 -> findTrainerById(scanner);
            case 9 -> listAllTrainers();
            case 10 -> createTraining(scanner);
            case 11 -> findTrainingById(scanner);
            case 12 -> listAllTrainings();
            case 13 -> System.exit(0);
            default -> logger.warn("Invalid choice. Please enter a number between 1 and 13.");
        }
    }

    private void displayWelcomeMessage() {
        System.out.println("======================================");
        System.out.println("||     Welcome to the Gym CRM CLI    ||");
        System.out.println("======================================");
    }

    private void displayMenu() {
        System.out.println("1. Create Trainee");
        System.out.println("2. Update Trainee");
        System.out.println("3. Find Trainee by ID");
        System.out.println("4. List All Trainees");
        System.out.println("5. Delete Trainee by ID\n");

        System.out.println("6. Create Trainer");
        System.out.println("7. Update Trainer");
        System.out.println("8. Find Trainer by ID");
        System.out.println("9. List All Trainers\n");

        System.out.println("10. Create Training");
        System.out.println("11. Find Training by ID");
        System.out.println("12. List All Trainings");
        System.out.println("13. Exit");
    }

    // ==== CRUD Methods for Trainee ==== //
    private void createTrainee(Scanner scanner) {
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

    private void updateTrainee(Scanner scanner) {
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

    private void findTraineeById(Scanner scanner) {
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

    private void listAllTrainees() {
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

    private void deleteTraineeById(Scanner scanner) {
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

    // ==== CRUD Methods for Trainer ==== //
    private void createTrainer(Scanner scanner) {
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

    private void updateTrainer(Scanner scanner) {
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

    private void findTrainerById(Scanner scanner) {
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

    private void listAllTrainers() {
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

    // ==== CRUD Methods for Training ==== //
    private void createTraining(Scanner scanner) {
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
            long trainingDuration = readLong(scanner);

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

    private void findTrainingById(Scanner scanner) {
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

    private void listAllTrainings() {
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

    // ==== Helper Methods ==== //
    private int readInt(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine();
            try {
                var number = Integer.parseInt(input);
                if (number < 0) {
                    throw new NumberFormatException();
                }
                return number;
            } catch (NumberFormatException e) {
                logger.error("Invalid number. Please try again.");
            }
        }
    }

    private long readLong(Scanner scanner) {
        while (true) {
            System.out.print("Enter Training Duration (in minutes): ");
            String input = scanner.nextLine();
            try {
                var number = Long.parseLong(input);
                if (number < 0) {
                    throw new NumberFormatException();
                }
                return number;
            } catch (NumberFormatException e) {
                logger.error("Invalid number. Please try again.");
            }
        }
    }

    private LocalDate readDate(Scanner scanner) {
        while (true) {
            System.out.print("Enter Date of Birth (yyyy-MM-dd): ");
            String input = scanner.nextLine();
            try {
                return LocalDate.parse(input, DATE_FORMATTER);
            } catch (DateTimeParseException e) {
                logger.error("Invalid date format. Please enter in yyyy-MM-dd format.");
            }
        }
    }

    private LocalDateTime readDateTime(Scanner scanner) {
        while (true) {
            System.out.print("Enter Training Date (yyyy-MM-dd'T'HH:mm:ss): ");
            String input = scanner.nextLine();
            try {
                return LocalDateTime.parse(input, DATE_TIME_FORMATTER);
            } catch (DateTimeParseException e) {
                logger.error("Invalid date format. Please enter in yyyy-MM-dd'T'HH:mm:ss format.");
            }
        }
    }

    private TrainingType readEnum(Scanner scanner) {
        while (true) {
            System.out.print("Enter Training Type (e.g., YOGA, CARDIO): ");
            String input = scanner.nextLine();
            try {
                return TrainingType.valueOf(input.toUpperCase());
            } catch (IllegalArgumentException e) {
                logger.error("Invalid option. Please enter one of the following: " +
                        String.join(", ", getEnumNames()));
            }
        }
    }

    private String[] getEnumNames() {
        return Arrays.stream(TrainingType.values())
                .map(Enum::name)
                .toArray(String[]::new);
    }

    private boolean isValidTraineeId(int traineeId) {
        boolean exists = gymFacade.findAllTrainees()
                .stream()
                .anyMatch(trainee -> trainee.getTraineeID() == traineeId);

        if (!exists) {
            logger.warn("Trainee with ID {} does not exist. Please enter a valid Trainee ID.", traineeId);
        }

        return !exists;
    }

    private boolean isValidTrainerId(int trainerId) {
        boolean exists = gymFacade.findAllTrainers()
                .stream()
                .anyMatch(trainer -> trainer.getTrainerId() == trainerId);

        if (!exists) {
            logger.warn("Trainer with ID {} does not exist. Please enter a valid Trainer ID.", trainerId);
        }

        return !exists;
    }

    private String readString(Scanner scanner, String prompt) {
        System.out.print(prompt);
        var input = scanner.nextLine();
        if(input.isBlank() || input.isEmpty()) {
            logger.error("Invalid input. Please try again.");
            return readString(scanner, prompt);
        }
        return input;
    }

    private void displaySeparator() {
        System.out.println("======================================");
    }
}