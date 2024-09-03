package com.gymcrm.facade;

import com.gymcrm.dto.*;
import com.gymcrm.exception.ResourceNotFoundException;
import com.gymcrm.model.User;
import com.gymcrm.service.TraineeService;
import com.gymcrm.service.TrainerService;
import com.gymcrm.service.TrainingService;
import com.gymcrm.service.UserService;
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
    public GymFacade(TrainerService trainerService, TraineeService traineeService, TrainingService trainingService,
                     UserService userService) {
        this.trainerService = trainerService;
        this.traineeService = traineeService;
        this.trainingService = trainingService;
        this.userService = userService;
    }

    public void saveTrainee(TraineeRequest request) {
        var user = toUser(request);
        userService.create(user);

        var trainee = toTrainee(request);
        traineeService.create(trainee, request.getTraineeId());
    }

    public void updateTrainee(TraineeRequest request) {
        var user = toUser(request);
        userService.update(user, user.getId());

        var trainee = toTrainee(request);
        traineeService.update(trainee, trainee.getId());
    }

    public TraineeResponse findTraineeById(int id) {
        var trainee = traineeService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Trainee not found"));
        var user = userService.findById(trainee.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("Trainee user not found"));
        return toTraineeResponse(trainee, user);
    }

    public List<TraineeResponse> findAllTrainees() {
        return traineeService.findAll().stream()
                .map(trainee -> {
                    User user = userService.findById(trainee.getUserId())
                            .orElseThrow(() -> new ResourceNotFoundException("Trainee user not found"));
                    return toTraineeResponse(trainee, user);
                }).toList();
    }

    public void deleteTrainee(int id) {
        traineeService.delete(id);
    }

    public void saveTrainer(TrainerRequest request) {
        var user = toUser(request);
        userService.create(user);

        var trainer = toTrainer(request);
        trainerService.create(trainer, trainer.getId());
    }

    public void updateTrainer(TrainerRequest request) {
        var user = toUser(request);
        userService.update(user, user.getId());

        var trainer = toTrainer(request);
        trainerService.update(trainer, trainer.getId());
    }

    public TrainerResponse findTrainerById(int id) {
        var trainer = trainerService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Trainer not found"));
        var user = userService.findById(trainer.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("Trainer user not found"));
        return toTrainerResponse(trainer, user);
    }

    public List<TrainerResponse> findAllTrainers() {
        return trainerService.findAll().stream()
                .map(trainer -> {
                    var user = userService.findById(trainer.getUserId())
                            .orElseThrow(() -> new ResourceNotFoundException("Trainer user not found"));
                    return toTrainerResponse(trainer, user);
                }).toList();
    }

    public void saveTraining(TrainingRequest trainingRequest) {
        var training = toTraining(trainingRequest);
        trainingService.create(training, training.getId());
    }

    public TrainingResponse findTrainingById(int id) {
        var training = trainingService.findById(id);
        if (training.isEmpty()) {
            throw new ResourceNotFoundException("Training not found");
        }
        var trainee = userService.findById(training.get().getTraineeId())
                .orElseThrow(() -> new ResourceNotFoundException("Trainee user not found"));
        var trainer = userService.findById(training.get().getTrainerId())
                .orElseThrow(() -> new ResourceNotFoundException("Trainer user not found"));
        return toTrainingResponse(training.get(), trainee, trainer);
    }

    public List<TrainingResponse> findAllTrainings() {
        return trainingService.findAll().stream()
                .map(training -> {
                    var trainee = userService.findById(training.getTraineeId())
                            .orElseThrow(() -> new ResourceNotFoundException("Trainee user not found"));
                    var trainer = userService.findById(training.getTrainerId())
                            .orElseThrow(() -> new ResourceNotFoundException("Trainer user not found"));
                    return toTrainingResponse(training, trainee, trainer);
                }).toList();
    }

    public List<User> findAllUsers() {
        return userService.findAll();
    }

}