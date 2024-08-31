package com.gymcrm.facade;

import com.gymcrm.dto.*;
import com.gymcrm.model.Trainee;
import com.gymcrm.model.User;
import com.gymcrm.service.TraineeService;
import com.gymcrm.service.TrainerService;
import com.gymcrm.service.TrainingService;
import com.gymcrm.service.UserService;
import com.gymcrm.util.UserUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.gymcrm.mapper.TraineeMapper.convertToTrainee;
import static com.gymcrm.mapper.TraineeMapper.convertToTraineeResponse;
import static com.gymcrm.mapper.TrainerMapper.convertToTrainer;
import static com.gymcrm.mapper.TrainerMapper.convertToTrainerResponse;
import static com.gymcrm.mapper.TrainingMapper.convertToTraining;
import static com.gymcrm.mapper.TrainingMapper.convertToTrainingResponse;
import static com.gymcrm.util.UserUtils.generateRandomPassword;
import static com.gymcrm.util.UserUtils.generateUsername;

@Component
public class GymFacade {
    private static final Logger logger = LoggerFactory.getLogger(GymFacade.class);
    private final TrainerService trainerService;
    private final TraineeService traineeService;
    private final TrainingService trainingService;
    private final UserService userService;

    @Autowired
    public GymFacade(TrainerService trainerService, TraineeService traineeService, TrainingService trainingService, UserService userService) {
        this.trainerService = trainerService;
        this.traineeService = traineeService;
        this.trainingService = trainingService;
        this.userService = userService;
    }

    public void saveTrainee(TraineeRequest request) {
        var existingUsernames = userService.getAllUsers().stream().map(User::getUsername).toList();
        var username = UserUtils.generateUsername(request.getFirstName(), request.getLastName(), existingUsernames);
        var password = generateRandomPassword();

        var user = new User();
        user.setId(request.getUserId());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setUsername(username);
        user.setPassword(password);
        user.setActive(true);

        userService.createUser(user);

        var trainee = new Trainee();
        trainee.setId(request.getTraineeId());
        trainee.setUserId(user.getId());
        trainee.setDateOfBirth(request.getDateOfBirth());
        trainee.setAddress(request.getAddress());

        traineeService.createTrainee(trainee);
    }

    public void updateTrainee(TraineeRequest request) {
        var user = new User();
        user.setId(request.getUserId());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setActive(true);
        user.setUsername(generateUsername(
                request.getFirstName(),
                request.getLastName(),
                userService.getAllUsers().stream().map(User::getUsername).toList()
        ));
        user.setPassword(generateRandomPassword());
        userService.updateUser(user);

        var trainee = convertToTrainee(request);
        traineeService.updateTrainee(trainee);
    }

    public TraineeResponse findTraineeById(int id) {
        var trainee = traineeService.getTrainee(id);
        if (trainee.isEmpty()) {
            logger.error("Trainee with id {} not found", id);
            throw new IllegalArgumentException("Trainee not found");
        }
        var user = userService.getUser(trainee.get().getUserId());
        if (user.isEmpty()) {
            logger.error("Trainee user with id {} not found", trainee.get().getUserId());
            throw new IllegalArgumentException("Trainee user not found");
        }
        return convertToTraineeResponse(trainee.get(), user.get());
    }

    public List<TraineeResponse> findAllTrainees() {
        var trainees = traineeService.getAllTrainees();
        return trainees.stream()
                .map(trainee -> {
                    var user = userService.getUser(trainee.getUserId());
                    if (user.isEmpty()) {
                        logger.error("Trainee user with id {} not found", trainee.getUserId());
                        throw new IllegalArgumentException("Trainee user not found");
                    }
                    return convertToTraineeResponse(trainee, user.get());
                }).toList();
    }

    public void deleteTrainee(int id) {
        traineeService.deleteTrainee(id);
    }

    public void saveTrainer(TrainerRequest request) {
        var user = new User();
        user.setId(request.getUserId());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setUsername(generateUsername(
                request.getFirstName(),
                request.getLastName(),
                userService.getAllUsers().stream().map(User::getUsername).toList()
        ));
        user.setPassword(generateRandomPassword());
        user.setActive(true);
        userService.createUser(user);

        var trainer = convertToTrainer(request);
        trainerService.createTrainer(trainer);
    }

    public void updateTrainer(TrainerRequest trainerRequest) {
        var user = new User();
        user.setId(trainerRequest.getUserId());
        user.setFirstName(trainerRequest.getFirstName());
        user.setLastName(trainerRequest.getLastName());
        user.setActive(true);
        user.setUsername(generateUsername(
                trainerRequest.getFirstName(),
                trainerRequest.getLastName(),
                userService.getAllUsers().stream().map(User::getUsername).toList()
        ));
        user.setPassword(generateRandomPassword());
        userService.updateUser(user);

        var trainer = convertToTrainer(trainerRequest);
        trainerService.updateTrainer(trainer);
    }

    public TrainerResponse findTrainerById(int id) {
        var trainer = trainerService.getTrainer(id);
        var user = userService.getUser(trainer.getUserId());
        if (user.isEmpty()) {
            logger.error("Trainer user with id {} not found", id);
            throw new IllegalArgumentException("Trainer user not found");
        }
        return convertToTrainerResponse(trainer, user.get());
    }

    public List<TrainerResponse> findAllTrainers() {
        var trainers = trainerService.getAllTrainer();
        return trainers.stream()
                .map(trainer -> {
                    var user = userService.getUser(trainer.getUserId());
                    if (user.isEmpty()) {
                        logger.error("Trainer user with id {} not found", trainer.getUserId());
                        throw new IllegalArgumentException("Trainer user not found");
                    }
                    return convertToTrainerResponse(trainer, user.get());
                }).toList();
    }

    public void saveTraining(TrainingRequest trainingRequest) {
        var training = convertToTraining(trainingRequest);
        trainingService.createTraining(training);
    }

    public TrainingResponse findTrainingById(int id) {
        var training = trainingService.getTraining(id);
        var trainee = userService.getUser(training.getTraineeId());
        var trainer = userService.getUser(training.getTrainerId());
        if (trainee.isEmpty()) {
            logger.error("Trainee user with id {} not found", training.getTraineeId());
            throw new IllegalArgumentException("Trainee user not found");
        }
        if (trainer.isEmpty()) {
            logger.error("Trainer user with id {} not found", training.getTrainerId());
            throw new IllegalArgumentException("Trainer user not found");
        }
        return convertToTrainingResponse(training, trainee.get(), trainer.get());
    }

    public List<TrainingResponse> findAllTrainings() {
        var trainings = trainingService.getAllTrainings();
        return trainings.stream()
                .map(training -> {
                    var trainee = userService.getUser(training.getTraineeId());
                    var trainer = userService.getUser(training.getTrainerId());
                    if (trainee.isEmpty()) {
                        logger.error("Trainee user with id {} not found", training.getTraineeId());
                        throw new IllegalArgumentException("Trainee user not found");
                    }
                    if (trainer.isEmpty()) {
                        logger.error("Trainer user with id {} not found", training.getTrainerId());
                        throw new IllegalArgumentException("Trainer user not found");
                    }
                    return convertToTrainingResponse(training, trainee.get(), trainer.get());
                }).toList();
    }

    public List<User> findAllUsers() {
        return userService.getAllUsers();
    }
}