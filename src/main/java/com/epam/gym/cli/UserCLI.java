package com.epam.gym.cli;

import com.epam.gym.exception.AuthenticationException;
import com.epam.gym.exception.ResourceNotFoundException;
import com.epam.gym.facade.GymFacade;
import com.epam.gym.model.User;
import com.epam.gym.security.AuthenticatedUser;
import com.epam.gym.security.AuthenticationContext;
import com.epam.gym.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Scanner;

import static com.epam.gym.cli.CLIHelper.readString;
import static com.epam.gym.cli.CLIHelper.readYesNo;
import static com.epam.gym.cli.CLIHelper.showUserLoginInfo;

/**
 * @deprecated As of version 1.1.0, replaced by controllers and a REST-based application.
 * Please use the respective REST endpoints to perform these operations.
 * @since 1.1.0
 */
@Deprecated(since = "1.1.0")
@Component
@Slf4j
@RequiredArgsConstructor
@Lazy
public class UserCLI {
    private static final Logger logger = LoggerFactory.getLogger("prompt-logger");
    private final GymFacade gymFacade;
    private final UserService userService;

    public boolean login(Scanner scanner) {
        logger.info("Please enter your username: ");
        String username = scanner.nextLine();
        logger.info("Please enter you password: ");
        String password = scanner.nextLine();

        Optional<User> userOptional = userService.findByUsernameAndPassword(username, password);

        if (userOptional.isPresent()) {
            User user = userOptional.get();

            AuthenticationContext.setAuthenticatedUser(AuthenticatedUser.builder()
                    .id(user.getId())
                    .username(user.getUsername())
                    .role(user.getRole())
                    .build());

            showUserLoginInfo(user);
            return true;
        } else {
            log.warn("Invalid username or password. Please try again.");
            return false;
        }
    }

    public boolean logout() {
        AuthenticationContext.clear();
        logger.info("Logged out successfully.\n");
        CLIHelper.displaySeparator();
        return true;
    }

    public void changeUserPassword(Scanner scanner, String username) {
        try {
            String oldPassword = readString(scanner, "Enter old password: ");
            String newPassword = readString(scanner, "Enter new password: ");
            String newPasswordRepeat = readString(scanner, "Repeat you new password: ");

            if (!gymFacade.login(username, oldPassword)) {
                throw new AuthenticationException("Wrong old password. Please try again.");
            }
            if (!newPassword.equals(newPasswordRepeat)) {
                throw new AuthenticationException("Your passwords aren't matching");
            }
            gymFacade.changeUserPassword(username, newPassword);
            logger.info("Password for user {} has been successfully updated\n", username);
            CLIHelper.displaySeparator();
        } catch (AuthenticationException e) {
            logger.error("🛑 Authentication Failed: {}\n", e.getMessage());
        } catch (Exception e) {
            logger.error("An unexpected error occurred: {}\n", e.getMessage());
        }
    }

    public void activateOrDeactivateUser(Scanner scanner, String username) {
        var user = gymFacade.findUserByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User nou found"));
        if (user.isActive()) {
            boolean deactivate = readYesNo(scanner, "Your account is activated, do you want to deactivate ? (Y/N): ");
            if (deactivate) {
                gymFacade.deactivateUser(username);
                logger.info("Your account is successfully deactivated!\n");
            }
            CLIHelper.displaySeparator();
        } else {
            boolean activate = readYesNo(scanner, "Your account is deactivated, do you want to activate ? (Y/N): ");
            if (activate) {
                gymFacade.activateUser(username);
                logger.info("Your account is successfully activated!\n");
            }
            CLIHelper.displaySeparator();
        }

    }
}
