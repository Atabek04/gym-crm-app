package com.epam.gym.cli;

import com.epam.gym.security.AuthenticatedUser;
import com.epam.gym.security.AuthenticationContext;
import com.epam.gym.security.UserRole;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;
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
            log.warn("👨🏻‍💻 You must be logged in to perform this action.");
            return;
        }

        UserRole role = authenticatedUser.getRole();
        String username = authenticatedUser.getUsername();

        switch (choice) {
            case 1 -> checkAndExecute(role, () -> traineeCLI.updateTrainee(scanner), UserRole.ROLE_TRAINEE);
            case 2 -> checkAndExecute(role, () -> userCLI.changeUserPassword(scanner, username), UserRole.ROLE_TRAINEE);
            case 3 -> checkAndExecute(role, () -> traineeCLI.findTraineeById(scanner), UserRole.ROLE_TRAINEE);
            case 4 -> checkAndExecute(role, traineeCLI::listAllTrainees, UserRole.ROLE_TRAINEE);
            case 5 -> checkAndExecute(role, () -> trainerCLI.listAllFreeTrainers(username), UserRole.ROLE_TRAINEE);
            case 6 -> checkAndExecute(role, () -> traineeCLI.deleteTraineeById(scanner), UserRole.ROLE_TRAINEE);
            case 7 -> checkAndExecute(role, () -> trainerCLI.updateTrainer(scanner), UserRole.ROLE_TRAINER);
            case 8 -> checkAndExecute(role, () -> userCLI.changeUserPassword(scanner, username), UserRole.ROLE_TRAINER);
            case 9 -> checkAndExecute(role, () -> trainerCLI.findTrainerById(scanner), UserRole.ROLE_TRAINER);
            case 10 -> checkAndExecute(role, trainerCLI::listAllTrainers, UserRole.ROLE_TRAINER);
            case 11 -> checkAndExecute(role, () -> trainingCLI.createTraining(scanner), UserRole.ROLE_TRAINEE);
            case 12 -> checkAndExecute(role, () -> trainingCLI.findTrainingById(scanner), UserRole.ROLE_TRAINEE);
            case 13 -> checkAndExecute(role, trainingCLI::listAllTrainings, UserRole.ROLE_TRAINEE, UserRole.ROLE_TRAINER);
            case 14 -> checkAndExecute(role, () -> trainingCLI.listAllTrainingsByCriteria(scanner),
                    UserRole.ROLE_TRAINEE, UserRole.ROLE_TRAINER);
            case 15 -> checkAndExecute(role, () -> userCLI.activateOrDeactivateUser(scanner, username),
                    UserRole.ROLE_TRAINER, UserRole.ROLE_TRAINEE);
            case 16 -> {
                if (userCLI.logout()) {
                    AuthenticationContext.setAuthenticatedUser(null);
                    promptLoginOrRegister(scanner);
                } else {
                    logger.info("Logout failed. Please try again.\n");
                }
            }
            default -> log.warn("⛔️ Invalid choice. Please enter a number between 1 and 15. 🔢");
        }
    }


    private void checkAndExecute(UserRole actualRole, Runnable action, UserRole... allowedRoles) {
        if (Arrays.stream(allowedRoles).anyMatch(role -> role == actualRole)) {
            action.run();
        } else {
            log.warn("🚫 You do not have the required role to perform this action.");
        }
    }



    private void displayWelcomeMessage() {
        logger.info("========================================\n");
        logger.info("|| 🏋🏻 ️Welcome to the Gym CRM CLI  🥊 ||\n");
        logger.info("========================================\n");
    }

    private void displayMenu() {
        logger.info("1. Update Trainee Profile ✏️\n");
        logger.info("2. Change trainee's password 🔑\n");
        logger.info("3. Find Trainee by ID 🔍\n");
        logger.info("4. List All Trainees 👥\n");
        logger.info("5. List All Free Trainers ✅\n");
        logger.info("6. Delete Trainee by ID 💢\n\n");

        logger.info("7. Update Trainer Profile ✏️\n");
        logger.info("8. Change trainer's password 🔑\n");
        logger.info("9. Find Trainer by ID 🔍\n");

        logger.info("10. List All Trainers 👥\n\n");

        logger.info("11. Create Training 📄\n");
        logger.info("12. Find Training by ID 🔍\n");
        logger.info("13. List All Trainings 🗂\n");
        logger.info("14. List All Training By Criteria 📝\n\n");

        logger.info("15. Activate/Deactivate account 🗝️\n");
        logger.info("16. Logout ⭕️\n");
    }
}
