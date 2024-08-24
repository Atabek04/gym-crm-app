package com.gymcrm;

import com.gymcrm.facade.GymFacade;
import com.gymcrm.model.Trainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class GymCLI {
    private final GymFacade gymFacade;

    @Autowired
    public GymCLI(GymFacade gymFacade) {
        this.gymFacade = gymFacade;
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("======================================");
        System.out.println("||     Welcome to the Gym CRM CLI    ||");
        System.out.println("======================================");

        while (true) {
            System.out.println("1. Register Trainer");
            System.out.println("2. List All Trainers");
            System.out.println("3. Exit");
            int choice = scanner.nextInt();
            scanner.nextLine();  // consume newline

            switch (choice) {
                case 1:
                    registerTrainer(scanner);
                    break;
                case 2:
                    listAllTrainers();
                    break;
                case 3:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice");
            }
        }
    }

    private void registerTrainer(Scanner scanner) {
        System.out.println("Enter Trainer ID:");
        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Enter User ID:");
        int userId = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Enter Specialization:");
        String specialization = scanner.nextLine();
        Trainer trainer = new Trainer(id, userId, specialization);
        gymFacade.registerTrainer(trainer);
        System.out.println("Trainer registered successfully.");
        System.out.println("======================================");
    }

    private void listAllTrainers() {
        System.out.println("All Trainers:");
        gymFacade.getAllTrainers().forEach(t ->
                System.out.println("ID: " + t.getId() + ", Specialization: " + t.getSpecialization())
        );
        System.out.println("======================================");
    }
}