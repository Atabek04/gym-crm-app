package com.epam.gym.cli;

import com.epam.gym.model.TrainingType;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.Scanner;

@Slf4j
public class CLIHelper {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    public static int readInt(Scanner scanner, String prompt) {
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
                log.error("Invalid number. Please try again.");
            }
        }
    }

    public static long readLong(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
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
            System.out.print("Enter Date of Birth (yyyy-MM-dd): ");
            String input = scanner.nextLine();
            try {
                LocalDate date = LocalDate.parse(input, DATE_FORMATTER);
                if (date.isAfter(LocalDate.now())) {
                    log.error("Date of birth cannot be in the future. Please enter a valid date.");
                    continue;
                }
                return date;
            } catch (DateTimeParseException e) {
                log.error("Invalid date format. Please enter in yyyy-MM-dd format.");
            }
        }
    }

    public static LocalDateTime readDateTime(Scanner scanner) {
        while (true) {
            System.out.print("Enter Training Date (yyyy-MM-dd'T'HH:mm:ss): ");
            String input = scanner.nextLine();
            try {
                LocalDateTime dateTime = LocalDateTime.parse(input, DATE_TIME_FORMATTER);
                if (dateTime.isBefore(LocalDateTime.now())) {
                    log.error("Training date and time cannot be in the past. Please enter a future date and time.");
                    continue;
                }
                return dateTime;
            } catch (DateTimeParseException e) {
                log.error("Invalid date format. Please enter in yyyy-MM-dd'T'HH:mm:ss format.");
            }
        }
    }

    public static TrainingType readEnum(Scanner scanner) {
        while (true) {
            System.out.print("Enter Training Type (e.g., YOGA, CARDIO): ");
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
        System.out.print(prompt);
        var input = scanner.nextLine();
        if (input.isBlank() || input.length() <= 2) {
            log.error("Invalid input. Please try again.");
            return readString(scanner, prompt);
        }
        return input;
    }

    public static void displaySeparator() {
        System.out.println("======================================");
    }
}