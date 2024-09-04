package com.gymcrm.cli;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Scanner;

import static com.gymcrm.cli.CLIHelper.readInt;

@Component
public class GymCLI {
    private final static Logger logger = LoggerFactory.getLogger(GymCLI.class);
    private final TraineeCLI traineeCLI;
    private final TrainerCLI trainerCLI;
    private final TrainingCLI trainingCLI;

    public GymCLI(TraineeCLI traineeCLI, TrainerCLI trainerCLI, TrainingCLI trainingCLI) {
        this.traineeCLI = traineeCLI;
        this.trainerCLI = trainerCLI;
        this.trainingCLI = trainingCLI;
    }

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

        System.out.println("Thank you for using the Gym CRM CLI. Goodbye!");
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


}