package com.epam.gym.cli;

import com.epam.gym.model.TrainingType;
import com.epam.gym.model.User;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.Scanner;

@Slf4j
@UtilityClass
public class CLIHelper {
    private static final Logger logger = LoggerFactory.getLogger("prompt-logger");
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm z");

    public static int readInt(Scanner scanner, String prompt) {
        while (true) {
            logger.info(prompt);
            String input = scanner.nextLine();
            try {
                var number = Integer.parseInt(input);
                if (number < 0) {
                    throw new NumberFormatException();
                }
                return number;
            } catch (NumberFormatException e) {
                log.error("Invalid number. Please try again.");
            }
        }
    }

    public static long readLong(Scanner scanner, String prompt) {
        while (true) {
            logger.info(prompt);
            String input = scanner.nextLine();
            try {
                var number = Long.parseLong(input);
                if (number < 0) {
                    throw new NumberFormatException();
                }
                return number;
            } catch (NumberFormatException e) {
                log.error("Invalid number. Please try again.");
            }
        }
    }

    public static LocalDate readDate(Scanner scanner) {
        while (true) {
            logger.info("Enter Date of Birth (yyyy-MM-dd): ");
            String input = scanner.nextLine();
            try {
                LocalDate date = LocalDate.parse(input, DATE_FORMATTER);
                if (date.isAfter(LocalDate.now())) {
                    logger.info("Date of birth cannot be in the future. Please enter a valid date.");
                    continue;
                }
                return date;
            } catch (DateTimeParseException e) {
                log.error("Invalid date format. Please enter in yyyy-MM-dd format.");
            }
        }
    }

    public static ZonedDateTime readDateTime(Scanner scanner) {
        while (true) {
            logger.info("Enter Training Date (yyyy-MM-dd HH:mm z): ");
            String input = scanner.nextLine();
            try {
                ZonedDateTime dateTime = ZonedDateTime.parse(input, DATE_TIME_FORMATTER);
                if (dateTime.isBefore(ZonedDateTime.now())) {
                    logger.info("Training date cannot be in the past. Please enter a valid date.\n");
                    continue;
                }
                return dateTime;
            } catch (DateTimeParseException e) {
                log.error("Invalid date format. Please enter in yyyy-MM-dd HH:mm z format.");
            }
        }
    }

    public static TrainingType readEnum(Scanner scanner) {
        while (true) {
            logger.info("Enter Training Type (e.g., YOGA, CARDIO): ");
            String input = scanner.nextLine();
            try {
                return TrainingType.valueOf(input.toUpperCase());
            } catch (IllegalArgumentException e) {
                log.error("Invalid option. Please enter one of the following: " +
                        String.join(", ", getEnumNames()));
            }
        }
    }

    public static String[] getEnumNames() {
        return Arrays.stream(TrainingType.values())
                .map(Enum::name)
                .toArray(String[]::new);
    }

    public static String readString(Scanner scanner, String prompt) {
        logger.info(prompt);
        var input = scanner.nextLine();
        if (input.isBlank() || input.length() <= 2) {
            log.error("Invalid input. Please try again.");
            return readString(scanner, prompt);
        }
        return input;
    }

    public static void showUserLoginInfo(User user) {
        displaySeparator();
        logger.info("🎉Login successful! Welcome, {} 👤\n", user.getUsername());
        var role = user.getRole().toString().substring(5).toLowerCase();
        logger.info("You are {}.\n", role);
        displaySeparator();
    }

    public static void displaySeparator() {
        logger.info("======================================\n");
    }
}