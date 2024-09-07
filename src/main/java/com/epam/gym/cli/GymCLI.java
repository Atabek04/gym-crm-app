package com.epam.gym.cli;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Scanner;

import static com.epam.gym.cli.CLIHelper.readInt;

@Component
@Slf4j
@RequiredArgsConstructor
public class GymCLI {
    private static final Logger logger = LoggerFactory.getLogger("prompt-logger");
    private final TraineeCLI traineeCLI;
    private final TrainerCLI trainerCLI;
    private final TrainingCLI trainingCLI;

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
                log.error("An error occurred: ", ex);
            }
        }

        logger.info("Thank you for using the Gym CRM CLI. Goodbye!");
    }

    private void handleUserChoice(int choice, Scanner scanner) {
        switch (choice) {
            case 1 -> traineeCLI.createTrainee(scanner);
            case 2 -> traineeCLI.updateTrainee(scanner);
            case 3 -> traineeCLI.findTraineeById(scanner);
            case 4 -> traineeCLI.listAllTrainees();
            case 5 -> traineeCLI.deleteTraineeById(scanner);
            case 6 -> trainerCLI.createTrainer(scanner);
            case 7 -> trainerCLI.updateTrainer(scanner);
            case 8 -> trainerCLI.findTrainerById(scanner);
            case 9 -> trainerCLI.listAllTrainers();
            case 10 -> trainingCLI.createTraining(scanner);
            case 11 -> trainingCLI.findTrainingById(scanner);
            case 12 -> trainingCLI.listAllTrainings();
            case 13 -> System.exit(0);
            default -> log.warn("Invalid choice. Please enter a number between 1 and 13.");
        }
    }

    private void displayWelcomeMessage() {
        logger.info("======================================\n");
        logger.info("||     Welcome to the Gym CRM CLI    |\n");
        logger.info("======================================\n");
    }

    private void displayMenu() {
        logger.info("1. Create Trainee\n");
        logger.info("2. Update Trainee\n");
        logger.info("3. Find Trainee by ID\n");
        logger.info("4. List All Trainees\n");
        logger.info("5. Delete Trainee by ID\n\n");

        logger.info("6. Create Trainer\n");
        logger.info("7. Update Trainer\n");
        logger.info("8. Find Trainer by ID\n");
        logger.info("9. List All Trainers\n\n");

        logger.info("10. Create Training\n");
        logger.info("11. Find Training by ID\n");
        logger.info("12. List All Trainings\n");
        logger.info("13. Exit\n");
    }


}