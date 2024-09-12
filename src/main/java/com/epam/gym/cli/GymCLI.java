package com.epam.gym.cli;

import com.epam.gym.security.AuthenticatedUser;
import com.epam.gym.security.AuthenticationContext;
import com.epam.gym.security.UserRole;
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
    private final UserCLI userCLI;
    private boolean isRunning = true;

    public void start() {
        Scanner scanner = new Scanner(System.in);
        displayWelcomeMessage();


        while (isRunning) {
            promptLoginOrRegister(scanner);
            while (AuthenticationContext.getAuthenticatedUser() != null) {
                displayMenu();
                try {
                    int choice = readInt(scanner, "Choose an option: ");
                    handleUserChoice(choice, scanner);
                    if (choice == 13) {
                        break;
                    }
                } catch (Exception ex) {
                    log.error("An error occurred: ", ex);
                }
            }
        }

        logger.info("Thank you for using the Gym App 🫶🏼Goodbye!");
    }

    private void promptLoginOrRegister(Scanner scanner) {
        while (AuthenticationContext.getAuthenticatedUser() == null && isRunning) {
            logger.info("1. Login 🔐\n");
            logger.info("2. Register as Trainee 🥋\n");
            logger.info("3. Register as Trainer 👨🏻‍🏫\n");
            logger.info("4. Exit 🚪\n");

            int choice = readInt(scanner, "Choose an option: ");
            switch (choice) {
                case 1:
                    if (userCLI.login(scanner)) {
                        return;
                    } else {
                        logger.info("⚠️Login failed. Please try again.🔄\n");
                    }
                    break;
                case 2:
                    traineeCLI.createTrainee(scanner);
                    break;
                case 3:
                    trainerCLI.createTrainer(scanner);
                    break;
                case 4:
                    isRunning = false;
                    break;
                default:
                    log.warn("⛔️Invalid choice. Please enter a number between 1 and 4.🔢");
            }
        }
    }

    private void handleUserChoice(int choice, Scanner scanner) {
        AuthenticatedUser authenticatedUser = AuthenticationContext.getAuthenticatedUser();

        if (authenticatedUser == null) {
            log.warn("👨🏻‍💻You must be logged in to perform this action.");
            return;
        }

        UserRole role = authenticatedUser.getRole();
        switch (choice) {
            case 1 -> checkAndExecute(role, UserRole.ROLE_TRAINEE, () -> traineeCLI.updateTrainee(scanner));
            case 2 -> checkAndExecute(role, UserRole.ROLE_TRAINEE,
                    () -> userCLI.changeUserPassword(scanner, authenticatedUser.getUsername()));
            case 3 -> checkAndExecute(role, UserRole.ROLE_TRAINEE, () -> traineeCLI.findTraineeById(scanner));
            case 4 -> checkAndExecute(role, UserRole.ROLE_TRAINEE, traineeCLI::listAllTrainees);
            case 5 -> checkAndExecute(role, UserRole.ROLE_TRAINEE, () -> traineeCLI.deleteTraineeById(scanner));
            case 6 -> checkAndExecute(role, UserRole.ROLE_TRAINER, () -> trainerCLI.updateTrainer(scanner));
            case 7 -> checkAndExecute(role, UserRole.ROLE_TRAINER,
                    () -> userCLI.changeUserPassword(scanner, authenticatedUser.getUsername()));
            case 8 -> checkAndExecute(role, UserRole.ROLE_TRAINER, () -> trainerCLI.findTrainerById(scanner));
            case 9 -> checkAndExecute(role, UserRole.ROLE_TRAINER, trainerCLI::listAllTrainers);
            case 10 -> checkAndExecute(role, UserRole.ROLE_TRAINEE, () -> trainingCLI.createTraining(scanner));
            case 11 -> checkAndExecute(role, UserRole.ROLE_TRAINEE, () -> trainingCLI.findTrainingById(scanner));
            case 12 -> checkAndExecute(role, UserRole.ROLE_TRAINEE, trainingCLI::listAllTrainings);
            case 13 -> {
                if (userCLI.logout()) {
                    AuthenticationContext.setAuthenticatedUser(null);
                } else {
                    logger.info("Logout failed. Please try again.\n");
                }
            }
            default -> log.warn("⛔️Invalid choice. Please enter a number between 1 and 13.🔢");
        }
    }

    private void checkAndExecute(UserRole actualRole, UserRole requiredRole, Runnable action) {
        if (actualRole == requiredRole) {
            action.run();
        } else {
            log.warn("🚫You do not have the required role to perform this action.");
        }
    }

    private void displayWelcomeMessage() {
        logger.info("========================================\n");
        logger.info("|| 🏋🏻 ️Welcome to the Gym CRM CLI  🥊 ||\n");
        logger.info("========================================\n");
    }

    private void displayMenu() {
        logger.info("1. Update Trainee Profile 📝\n");
        logger.info("2. Change trainee's password 🔑\n");
        logger.info("3. Find Trainee by ID 🔍\n");
        logger.info("4. List All Trainees 👥\n");
        logger.info("5. Delete Trainee by ID 💢\n\n");

        logger.info("6. Update Trainer Profile\n");
        logger.info("7. Change trainer's password 🔑\n");
        logger.info("8. Find Trainer by ID 🔍\n");
        logger.info("9. List All Trainers 👥\n\n");

        logger.info("10. Create Training 📄\n");
        logger.info("11. Find Training by ID 🔍\n");
        logger.info("12. List All Trainings 🗂\n️");
        logger.info("13. Logout 🕳️\n");
    }
}
