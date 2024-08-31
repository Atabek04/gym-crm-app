package com.gymcrm;

import com.gymcrm.dto.*;
import com.gymcrm.facade.GymFacade;
import com.gymcrm.model.TrainingType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
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
            System.out.println("12. List All Trainings\n");

            System.out.println("13. Exit\n");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    createTrainee(scanner);
                    break;
                case 2:
                    updateTrainee(scanner);
                    break;
                case 3:
                    findTraineeById(scanner);
                    break;
                case 4:
                    listAllTrainees();
                    break;
                case 5:
                    deleteTraineeById(scanner);
                    break;
                case 6:
                    createTrainer(scanner);
                    break;
                case 7:
                    updateTrainer(scanner);
                    break;
                case 8:
                    findTrainerById(scanner);
                    break;
                case 9:
                    listAllTrainers();
                    break;
                case 10:
                    createTraining(scanner);
                    break;
                case 11:
                    findTrainingById(scanner);
                    break;
                case 12:
                    listAllTrainings();
                    break;
                case 13:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice");
            }
        }
    }

    private void createTrainee(Scanner scanner) {
        System.out.print("Enter Trainee ID:");
        int traineeId = scanner.nextInt();
        scanner.nextLine();
        var traineeExists = gymFacade.findAllTrainees()
                .stream().anyMatch(trainee -> trainee.getTraineeID() == traineeId);
        if (traineeExists) {
            System.out.println("Trainee with this ID already exists. Please enter a different ID.");
            return;
        }

        System.out.print("Enter User ID: ");
        int userId = scanner.nextInt();
        scanner.nextLine();
        var userExists = gymFacade.findAllUsers()
                .stream().anyMatch(user -> user.getId() == userId);
        if (userExists) {
            System.out.println("User with this ID already exists. Please enter a different ID.");
            return;
        }

        System.out.print("Enter First Name: ");
        String firstName = scanner.nextLine();
        System.out.print("Enter Last Name: ");
        String lastName = scanner.nextLine();
        System.out.print("Enter Date of Birth (yyyy-mm-dd): ");
        LocalDate dateOfBirth = LocalDate.parse(scanner.nextLine());
        System.out.print("Enter Address: ");
        String address = scanner.nextLine();

        TraineeRequest request = new TraineeRequest(traineeId, userId, firstName, lastName, dateOfBirth, address);
        gymFacade.saveTrainee(request);
        System.out.println("Trainee created successfully.");
        System.out.println("======================================");
    }

    private void updateTrainee(Scanner scanner) {
        System.out.print("Enter Trainee ID: ");
        int traineeId = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter User ID: ");
        int userId = scanner.nextInt();
        scanner.nextLine();
        var userExists = gymFacade.findAllUsers()
                .stream().anyMatch(user -> user.getId() == userId);
        if (userExists) {
            System.out.println("User with this ID already exists. Please enter a different ID.");
            return;
        }

        System.out.print("Enter First Name: ");
        String firstName = scanner.nextLine();
        System.out.print("Enter Last Name: ");
        String lastName = scanner.nextLine();
        System.out.print("Enter Date of Birth (yyyy-mm-dd): ");
        LocalDate dateOfBirth = LocalDate.parse(scanner.nextLine());
        System.out.print("Enter Address: ");
        String address = scanner.nextLine();

        TraineeRequest request = new TraineeRequest(traineeId, userId, firstName, lastName, dateOfBirth, address);
        gymFacade.updateTrainee(request);
        System.out.println("Trainee updated successfully.");
        System.out.println("======================================");
    }

    private void findTraineeById(Scanner scanner) {
        System.out.print("Enter Trainee ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        TraineeResponse traineeResponse = gymFacade.findTraineeById(id);
        if (traineeResponse != null) {
            System.out.println("Trainee found: " + traineeResponse);
        } else {
            System.out.println("Trainee not found.");
        }
        System.out.println("======================================");
    }

    private void listAllTrainees() {
        List<TraineeResponse> trainees = gymFacade.findAllTrainees();
        System.out.println("All Trainees:");
        trainees.forEach(System.out::println);
        System.out.println("======================================");
    }

    private void deleteTraineeById(Scanner scanner) {
        System.out.print("Enter Trainee ID to Delete: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        gymFacade.deleteTrainee(id);
        System.out.println("Trainee deleted successfully.");
        System.out.println("======================================");
    }

    private void createTrainer(Scanner scanner) {
        System.out.print("Enter Trainer ID: ");
        int trainerId = scanner.nextInt();
        scanner.nextLine();
        var trainerExists = gymFacade.findAllTrainers()
                .stream().anyMatch(trainer -> trainer.getTrainerId() == trainerId);
        if (trainerExists) {
            System.out.println("Trainer with this ID already exists. Please enter a different ID.");
            return;
        }

        System.out.print("Enter User ID: ");
        int userId = scanner.nextInt();
        scanner.nextLine();
        var userExists = gymFacade.findAllUsers()
                .stream().anyMatch(user -> user.getId() == userId);
        if (userExists) {
            System.out.println("User with this ID already exists. Please enter a different ID.");
            return;
        }

        System.out.print("Enter First Name: ");
        String firstName = scanner.nextLine();
        System.out.print("Enter Last Name: ");
        String lastName = scanner.nextLine();
        System.out.print("Enter Specialization: ");
        String specialization = scanner.nextLine();

        TrainerRequest request = new TrainerRequest(trainerId, userId, firstName, lastName, specialization);
        gymFacade.saveTrainer(request);
        System.out.println("Trainer created successfully.");
        System.out.println("======================================");
    }

    private void updateTrainer(Scanner scanner) {
        System.out.print("Enter Trainer ID to Update: ");
        int trainerId = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter User ID: ");
        int userId = scanner.nextInt();
        scanner.nextLine();
        var userExists = gymFacade.findAllUsers()
                .stream().anyMatch(user -> user.getId() == userId);
        if (userExists) {
            System.out.println("User with this ID already exists. Please enter a different ID.");
            return;
        }

        System.out.print("Enter First Name: ");
        String firstName = scanner.nextLine();
        System.out.print("Enter Last Name: ");
        String lastName = scanner.nextLine();
        System.out.print("Enter Specialization: ");
        String specialization = scanner.nextLine();

        TrainerRequest request = new TrainerRequest(trainerId, userId, firstName, lastName, specialization);
        gymFacade.updateTrainer(request);
        System.out.println("Trainer updated successfully.");
        System.out.println("======================================");
    }

    private void findTrainerById(Scanner scanner) {
        System.out.print("Enter Trainer ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        TrainerResponse trainerResponse = gymFacade.findTrainerById(id);
        if (trainerResponse != null) {
            System.out.println("Trainer found: " + trainerResponse);
        } else {
            System.out.println("Trainer not found.");
        }
        System.out.println("======================================");
    }

    private void listAllTrainers() {
        List<TrainerResponse> trainers = gymFacade.findAllTrainers();
        System.out.println("All Trainers:");
        trainers.forEach(System.out::println);
        System.out.println("======================================");
    }

    private void createTraining(Scanner scanner) {
        System.out.print("Enter Training ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        var trainingExists = gymFacade.findAllTrainings()
                .stream().anyMatch(training -> training.getId() == id);
        if (trainingExists) {
            System.out.println("Training with this ID already exists. Please enter a different ID.");
            return;
        }

        System.out.print("Enter Trainer ID: ");
        int trainerId = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter Trainee ID: ");
        int traineeId = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter Training Name: ");
        String trainingName = scanner.nextLine();
        System.out.print("Enter Training Type (e.g., YOGA, CARDIO): ");
        String trainingType = scanner.nextLine();
        System.out.print("Enter Training Date (yyyy-MM-dd'T'HH:mm:ss): ");
        String trainingDateStr = scanner.nextLine();
        LocalDateTime trainingDate = LocalDateTime.parse(trainingDateStr);
        System.out.print("Enter Training Duration (in minutes): ");
        long trainingDuration = scanner.nextLong();
        scanner.nextLine();

        TrainingRequest request = new TrainingRequest(id, traineeId, trainerId, trainingName, TrainingType.valueOf(trainingType), trainingDate, trainingDuration);
        gymFacade.saveTraining(request);
        System.out.println("Training created successfully.");
        System.out.println("======================================");
    }

    private void findTrainingById(Scanner scanner) {
        System.out.print("Enter Training ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        TrainingResponse trainingResponse = gymFacade.findTrainingById(id);
        if (trainingResponse != null) {
            System.out.println("Training found: " + trainingResponse);
        } else {
            System.out.println("Training not found.");
        }
        System.out.println("======================================");
    }

    private void listAllTrainings() {
        List<TrainingResponse> trainings = gymFacade.findAllTrainings();
        System.out.println("All Trainings:");
        trainings.forEach(System.out::println);
        System.out.println("======================================");
    }
}