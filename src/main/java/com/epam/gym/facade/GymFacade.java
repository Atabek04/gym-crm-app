package com.epam.gym.facade;

import com.epam.gym.dto.*;
import com.epam.gym.exception.ResourceNotFoundException;
import com.epam.gym.model.User;
import com.epam.gym.service.TraineeService;
import com.epam.gym.service.TrainerService;
import com.epam.gym.service.TrainingService;
import com.epam.gym.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.epam.gym.mapper.TraineeMapper.toTrainee;
import static com.epam.gym.mapper.TraineeMapper.toTraineeResponse;
import static com.epam.gym.mapper.TrainerMapper.toTrainer;
import static com.epam.gym.mapper.TrainerMapper.toTrainerResponse;
import static com.epam.gym.mapper.TrainingMapper.toTraining;
import static com.epam.gym.mapper.TrainingMapper.toTrainingResponse;
import static com.epam.gym.mapper.UserMapper.toUser;

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
        userService.create(user);

        var trainee = toTrainee(request);
        traineeService.create(trainee, request.traineeId());
    }

    @Transactional
    public void updateTrainee(TraineeRequest request) {
        var user = toUser(request);
        userService.update(user, user.getId());

        var trainee = toTrainee(request);
        traineeService.update(trainee, trainee.getId());
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

    public void deleteTrainee(Long id) {
        traineeService.delete(id);
    }

    @Transactional
    public void saveTrainer(TrainerRequest request) {
        var user = toUser(request);
        userService.create(user);

        var trainer = toTrainer(request);
        trainerService.create(trainer, trainer.getId());
    }

    @Transactional
    public void updateTrainer(TrainerRequest request) {
        var user = toUser(request);
        userService.update(user, user.getId());

        var trainer = toTrainer(request);
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
        var training = toTraining(trainingRequest);
        trainingService.create(training, training.getId());
    }

    public TrainingResponse findTrainingById(Long id) {
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