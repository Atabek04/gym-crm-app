package com.epam.gym.facade;

import com.epam.gym.dto.TraineeRequest;
import com.epam.gym.dto.TraineeResponse;
import com.epam.gym.dto.TrainerRequest;
import com.epam.gym.dto.TrainerResponse;
import com.epam.gym.dto.TrainingRequest;
import com.epam.gym.dto.TrainingResponse;
import com.epam.gym.exception.ResourceNotFoundException;
import com.epam.gym.mapper.TraineeMapper;
import com.epam.gym.mapper.TrainerMapper;
import com.epam.gym.mapper.TrainingMapper;
import com.epam.gym.model.Trainee;
import com.epam.gym.model.Trainer;
import com.epam.gym.model.Training;
import com.epam.gym.model.User;
import com.epam.gym.security.UserRole;
import com.epam.gym.service.TraineeService;
import com.epam.gym.service.TrainerService;
import com.epam.gym.service.TrainingService;
import com.epam.gym.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static com.epam.gym.mapper.TraineeMapper.toTrainee;
import static com.epam.gym.mapper.TraineeMapper.toTraineeResponse;
import static com.epam.gym.mapper.TrainerMapper.toTrainer;
import static com.epam.gym.mapper.TrainerMapper.toTrainerResponse;
import static com.epam.gym.mapper.TrainingMapper.toTraining;
import static com.epam.gym.mapper.TrainingMapper.toTrainingResponse;
import static com.epam.gym.mapper.UserMapper.toUser;

/**
 * @deprecated As of version 1.1.0, replaced by controllers and a REST-based application.
 * Please use the respective REST endpoints to perform these operations.
 * @since 1.1.0
 */
@Deprecated(since = "1.1.0")
@Component
@RequiredArgsConstructor
@Slf4j
public class GymFacade {

    private final TrainerService trainerService;
    private final TraineeService traineeService;
    private final TrainingService trainingService;
    private final UserService userService;


    @Transactional
    public Optional<Trainee> saveTrainee(TraineeRequest request) {
        User user = toUser(request);
        user.setRole(UserRole.ROLE_TRAINEE);
        var createdUser = userService.create(user)
                .orElseThrow(() -> new ResourceNotFoundException("Created trainee not found"));

        Trainee trainee = toTrainee(request, createdUser);
        return traineeService.create(trainee);
    }

    @Transactional
    public void updateTrainee(TraineeRequest request, Long traineeId) {
        var user = toUser(request);
        var userId = traineeService.findById(traineeId)
                .orElseThrow(() -> new ResourceNotFoundException("Trainee not found")).getUser().getId();
        user.setId(userId);
        userService.update(user, userId);

        var trainee = toTrainee(request, user);
        trainee.setId(traineeId);
        traineeService.update(trainee, traineeId);
    }

    public TraineeResponse findTraineeById(Long id) {
        var trainee = traineeService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Trainee not found"));
        return toTraineeResponse(trainee);
    }

    public List<TraineeResponse> findAllTrainees() {
        return traineeService.findAll()
                .stream()
                .sorted(Comparator.comparing(Trainee::getId))
                .map(TraineeMapper::toTraineeResponse)
                .toList();
    }

    @Transactional
    public void deleteTrainee(Long id) {
        traineeService.delete(id);
    }

    @Transactional
    public Optional<Trainer> saveTrainer(TrainerRequest request) {
        var user = toUser(request);
        user.setRole(UserRole.ROLE_TRAINER);
        var createdUser = userService.create(user)
                .orElseThrow(() -> new ResourceNotFoundException("Created User not returned"));

        var trainer = toTrainer(request, createdUser);
        return trainerService.create(trainer);
    }

    @Transactional
    public void updateTrainer(TrainerRequest request, Long trainerId) {
        var user = toUser(request);
        var userId = trainerService.findById(trainerId)
                .orElseThrow(() -> new ResourceNotFoundException("Trainer not found")).getUser().getId();
        user.setId(userId);
        userService.update(user, userId);

        var trainer = toTrainer(request, user);
        trainer.setId(trainerId);
        trainerService.update(trainer, trainer.getId());
    }

    public TrainerResponse findTrainerById(Long id) {
        var trainer = trainerService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Trainer not found"));
        return toTrainerResponse(trainer);
    }

    public List<TrainerResponse> findAllTrainers() {
        return trainerService.findAll()
                .stream()
                .sorted(Comparator.comparing(Trainer::getId))
                .map(TrainerMapper::toTrainerResponse)
                .toList();
    }

    @Transactional
    public void saveTraining(TrainingRequest trainingRequest) {
        var trainee = traineeService.findById(trainingRequest.traineeId())
                .orElseThrow(() -> new ResourceNotFoundException("Facade:: Trainee not found"));
        var trainer = trainerService.findById(trainingRequest.trainerId())
                .orElseThrow(() -> new ResourceNotFoundException("Facade:: Trainer not found"));

        var training = toTraining(trainingRequest, trainee, trainer);
        trainingService.create(training);
    }

    public TrainingResponse findTrainingById(Long id) {
        var training = trainingService.findById(id);
        if (training.isEmpty()) {
            throw new ResourceNotFoundException("Training not found");
        }
        return toTrainingResponse(training.get());
    }

    public List<TrainingResponse> findAllTrainings() {
        return trainingService.findAll()
                .stream()
                .sorted(Comparator.comparing(Training::getId))
                .map(TrainingMapper::toTrainingResponse)
                .toList();
    }

    public boolean login(String username, String password) {
        return userService.findByUsernameAndPassword(username, password).isPresent();
    }

    public void changeUserPassword(String username, String newPassword) {
        userService.changePassword(username, newPassword);
    }

    public List<TrainerResponse> listAllFreeTrainers(String username) {
        List<Trainer> trainers = trainerService.findAllFreeTrainers(username);
        return trainers.stream().map(TrainerMapper::toTrainerResponse).toList();
    }

    public void activateUser(String username) {
        userService.activateUser(username);
    }

    public void deactivateUser(String username) {
        userService.deactivateUser(username);
    }

    public Optional<User> findUserByUsername(String username) {
        return userService.findUserByUsername(username);
    }

    public List<TrainingResponse> findTrainingsByCriteria(Long trainerId,
                                                          Long traineeId,
                                                          LocalDate startDate,
                                                          LocalDate endDate,
                                                          Integer typeId,
                                                          String sortBy,
                                                          boolean ascending) {
        List<Training> trainings = trainingService.findTrainingsByCriteria(
                trainerId,
                traineeId,
                startDate,
                endDate,
                typeId,
                sortBy,
                ascending
        );
        return trainings.stream().map(TrainingMapper::toTrainingResponse).toList();
    }
}