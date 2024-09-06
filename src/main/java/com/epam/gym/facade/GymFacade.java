package com.epam.gym.facade;

import com.epam.gym.dto.*;
import com.epam.gym.exception.ResourceNotFoundException;
import com.epam.gym.model.Trainee;
import com.epam.gym.model.Trainer;
import com.epam.gym.model.Training;
import com.epam.gym.model.User;
import com.epam.gym.service.TraineeService;
import com.epam.gym.service.TrainerService;
import com.epam.gym.service.TrainingService;
import com.epam.gym.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.epam.gym.mapper.TraineeMapper.toTrainee;
import static com.epam.gym.mapper.TraineeMapper.toTraineeResponse;
import static com.epam.gym.mapper.TrainerMapper.toTrainer;
import static com.epam.gym.mapper.TrainerMapper.toTrainerResponse;
import static com.epam.gym.mapper.TrainingMapper.toTraining;
import static com.epam.gym.mapper.TrainingMapper.toTrainingResponse;
import static com.epam.gym.mapper.UserMapper.toUser;
import static com.epam.gym.util.IDGenerator.generateId;

@Component
@RequiredArgsConstructor
public class GymFacade {

    private final TrainerService trainerService;
    private final TraineeService traineeService;
    private final TrainingService trainingService;
    private final UserService userService;

    @Transactional
    public void saveTrainee(TraineeRequest request) {
        var user = toUser(request);
        var newUserId = generateId(userService.findAll().stream().map(User::getId).collect(Collectors.toSet()));
        user.setId(newUserId);
        userService.create(user, newUserId);

        var trainee = toTrainee(request);
        var newTraineeId = generateId(traineeService.findAll().stream().map(Trainee::getId).collect(Collectors.toSet()));
        trainee.setUserId(newUserId);
        trainee.setId(newTraineeId);
        traineeService.create(trainee, newTraineeId);
    }

    @Transactional
    public void updateTrainee(TraineeRequest request, Long traineeId) {
        var user = toUser(request);
        var userId = traineeService.findById(traineeId)
                .orElseThrow(() -> new ResourceNotFoundException("Trainee not found"))
                .getUserId();
        user.setId(userId);
        userService.update(user, userId);

        var trainee = toTrainee(request);
        trainee.setId(traineeId);
        trainee.setUserId(userId);
        traineeService.update(trainee, traineeId);
    }

    public TraineeResponse findTraineeById(Long id) {
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

    @Transactional
    public void deleteTrainee(Long id) {
        var userId = traineeService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Trainee not found"))
                .getUserId();
        traineeService.delete(id);
        userService.delete(userId);
    }

    @Transactional
    public void saveTrainer(TrainerRequest request) {
        var user = toUser(request);
        var newUserId = generateId(userService.findAll().stream().map(User::getId).collect(Collectors.toSet()));
        user.setId(newUserId);
        userService.create(user, newUserId);

        var trainer = toTrainer(request);
        var newTrainerId = generateId(trainerService.findAll().stream().map(Trainer::getId).collect(Collectors.toSet()));
        trainer.setId(newTrainerId);
        trainer.setUserId(newUserId);
        trainerService.create(trainer, newTrainerId);
    }

    @Transactional
    public void updateTrainer(TrainerRequest request, Long trainerId) {
        var user = toUser(request);
        var userId = trainerService.findById(trainerId)
                .orElseThrow(() -> new ResourceNotFoundException("Trainer not found"))
                .getUserId();
        user.setId(userId);
        userService.update(user, userId);

        var trainer = toTrainer(request);
        trainer.setUserId(userId);
        trainer.setId(trainerId);
        trainerService.update(trainer, trainer.getId());
    }

    public TrainerResponse findTrainerById(Long id) {
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
        var newTrainingId = generateId(trainingService.findAll().stream().map(Training::getId).collect(Collectors.toSet()));

        var training = toTraining(trainingRequest);
        training.setId(newTrainingId);
        trainingService.create(training, newTrainingId);
    }

    public TrainingResponse findTrainingById(Long id) {
        var training = trainingService.findById(id);
        if (training.isEmpty()) {
            throw new ResourceNotFoundException("Training not found");
        }

        var traineeUserId = traineeService.findById(training.get().getTraineeId())
                .orElseThrow(() -> new ResourceNotFoundException("Trainee not found"))
                .getUserId();
        var userIdTrainer = trainerService.findById(training.get().getTrainerId())
                .orElseThrow(() -> new ResourceNotFoundException("Trainer not found"))
                .getUserId();
        var trainee = userService.findById(traineeUserId)
                .orElseThrow(() -> new ResourceNotFoundException("Trainee user not found"));
        var trainer = userService.findById(userIdTrainer)
                .orElseThrow(() -> new ResourceNotFoundException("Trainer user not found"));
        return toTrainingResponse(training.get(), trainee, trainer);
    }

    public List<TrainingResponse> findAllTrainings() {
        return trainingService.findAll().stream()
                .map(training -> {
                    var traineeUserId = traineeService.findById(training.getTraineeId())
                            .orElseThrow(() -> new ResourceNotFoundException("Trainee not found"))
                            .getUserId();
                    var userIdTrainer = trainerService.findById(training.getTrainerId())
                            .orElseThrow(() -> new ResourceNotFoundException("Trainer not found"))
                            .getUserId();
                    var trainee = userService.findById(traineeUserId)
                            .orElseThrow(() -> new ResourceNotFoundException("Trainee user not found"));
                    var trainer = userService.findById(userIdTrainer)
                            .orElseThrow(() -> new ResourceNotFoundException("Trainer user not found"));

                    return toTrainingResponse(training, trainee, trainer);
                }).toList();
    }

    public List<User> findAllUsers() {
        return userService.findAll();
    }

}