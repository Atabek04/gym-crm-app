package com.gymcrm.facade;

import com.gymcrm.dto.*;
import com.gymcrm.exception.ResourceNotFoundException;
import com.gymcrm.model.Trainee;
import com.gymcrm.model.User;
import com.gymcrm.service.TraineeService;
import com.gymcrm.service.TrainerService;
import com.gymcrm.service.TrainingService;
import com.gymcrm.service.UserService;
import com.gymcrm.util.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.gymcrm.mapper.TraineeMapper.toTrainee;
import static com.gymcrm.mapper.TraineeMapper.toTraineeResponse;
import static com.gymcrm.mapper.TrainerMapper.toTrainer;
import static com.gymcrm.mapper.TrainerMapper.toTrainerResponse;
import static com.gymcrm.mapper.TrainingMapper.toTraining;
import static com.gymcrm.mapper.TrainingMapper.toTrainingResponse;
import static com.gymcrm.mapper.UserMapper.toUser;

@Component
public class GymFacade {

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
        var user = convertToUser(request);
        userService.createUser(user);

        var trainee = toTrainee(request);
        trainee.setUserId(user.getId());
        trainee.setAddress(request.getAddress());
        trainee.setDateOfBirth(request.getDateOfBirth());
        traineeService.createTrainee(trainee);
    }

    public void updateTrainee(TraineeRequest request) {
        User user = convertToUser(request);
        userService.updateUser(user);

        var trainee = toTrainee(request);
        traineeService.updateTrainee(trainee);
    }

    public TraineeResponse findTraineeById(int id) {
        Trainee trainee = traineeService.getTrainee(id).orElseThrow(() -> new ResourceNotFoundException("Trainee not found"));
        User user = userService.getUser(trainee.getUserId()).orElseThrow(() -> new ResourceNotFoundException("Trainee user not found"));
        return toTraineeResponse(trainee, user);
    }

    public List<TraineeResponse> findAllTrainees() {
        return traineeService.getAllTrainees().stream()
                .map(trainee -> {
                    User user = userService.getUser(trainee.getUserId()).orElseThrow(() -> new ResourceNotFoundException("Trainee user not found"));
                    return toTraineeResponse(trainee, user);
                }).toList();
    }

    public void deleteTrainee(int id) {
        traineeService.deleteTrainee(id);
    }

    public void saveTrainer(TrainerRequest request) {
        User user = convertToUser(request);
        userService.createUser(user);

        var trainer = toTrainer(request);
        trainerService.createTrainer(trainer);
    }

    public void updateTrainer(TrainerRequest request) {
        User user = convertToUser(request);
        userService.updateUser(user);

        var trainer = toTrainer(request);
        trainerService.updateTrainer(trainer);
    }

    public TrainerResponse findTrainerById(int id) {
        var trainer = trainerService.getTrainer(id).orElseThrow(() -> new ResourceNotFoundException("Trainer not found"));
        var user = userService.getUser(trainer.getUserId()).orElseThrow(() -> new ResourceNotFoundException("Trainer user not found"));
        return toTrainerResponse(trainer, user);
    }

    public List<TrainerResponse> findAllTrainers() {
        return trainerService.getAllTrainers().stream()
                .map(trainer -> {
                    var user = userService.getUser(trainer.getUserId()).orElseThrow(() -> new ResourceNotFoundException("Trainer user not found"));
                    return toTrainerResponse(trainer, user);
                }).toList();
    }

    public void saveTraining(TrainingRequest trainingRequest) {
        var training = toTraining(trainingRequest);
        trainingService.createTraining(training);
    }

    public TrainingResponse findTrainingById(int id) {
        var training = trainingService.getTraining(id);
        var trainee = userService.getUser(training.getTraineeId()).orElseThrow(() -> new ResourceNotFoundException("Trainee user not found"));
        var trainer = userService.getUser(training.getTrainerId()).orElseThrow(() -> new ResourceNotFoundException("Trainer user not found"));
        return toTrainingResponse(training, trainee, trainer);
    }

    public List<TrainingResponse> findAllTrainings() {
        return trainingService.getAllTrainings().stream()
                .map(training -> {
                    var trainee = userService.getUser(training.getTraineeId()).orElseThrow(() -> new ResourceNotFoundException("Trainee user not found"));
                    var trainer = userService.getUser(training.getTrainerId()).orElseThrow(() -> new ResourceNotFoundException("Trainer user not found"));
                    return toTrainingResponse(training, trainee, trainer);
                }).toList();
    }

    public List<User> findAllUsers() {
        return userService.getAllUsers();
    }

    public User convertToUser(TraineeRequest request) {
        var username = UserUtils.generateUsername(request.getFirstName(), request.getLastName(),
                userService.getAllUsers().stream().map(User::getUsername).toList());
        var password = UserUtils.generateRandomPassword();
        return toUser(request, username, password);
    }

    public User convertToUser(TrainerRequest request) {
        var username = UserUtils.generateUsername(request.getFirstName(), request.getLastName(),
                userService.getAllUsers().stream().map(User::getUsername).toList());
        var password = UserUtils.generateRandomPassword();
        return toUser(request, username, password);
    }
}