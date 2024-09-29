package com.epam.gym.service.impl;

import com.epam.gym.dao.TraineeDAO;
import com.epam.gym.dao.TrainerDAO;
import com.epam.gym.dao.TrainingDAO;
import com.epam.gym.dto.BasicTrainerResponse;
import com.epam.gym.dto.TraineeRequest;
import com.epam.gym.dto.TraineeResponse;
import com.epam.gym.dto.TraineeTrainingFilterRequest;
import com.epam.gym.dto.TraineeUpdateRequest;
import com.epam.gym.dto.TrainingResponse;
import com.epam.gym.dto.UserCredentials;
import com.epam.gym.exception.ResourceNotFoundException;
import com.epam.gym.mapper.TrainerMapper;
import com.epam.gym.mapper.TrainingMapper;
import com.epam.gym.model.Trainee;
import com.epam.gym.model.Trainer;
import com.epam.gym.model.Training;
import com.epam.gym.service.TraineeService;
import com.epam.gym.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.epam.gym.mapper.TraineeMapper.toTrainee;
import static com.epam.gym.mapper.TraineeMapper.toTraineeResponse;
import static com.epam.gym.mapper.UserMapper.toUser;
import static com.epam.gym.util.Constants.DUMMY_TRAINING_DURATION;
import static com.epam.gym.util.Constants.DUMMY_TRAINING_NAME;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class TraineeServiceImpl implements TraineeService {
    private final TraineeDAO traineeDAO;
    private final UserService userService;
    private final TrainerDAO trainerDAO;
    private final TrainingDAO trainingDAO;

    @Transactional
    @Override
    public UserCredentials create(TraineeRequest request) {
        var createdUser = userService.create(toUser(request))
                .orElseThrow(() -> new IllegalStateException("Failed to create user"));
        traineeDAO.save(toTrainee(request, createdUser));

        return UserCredentials.builder()
                .username(createdUser.getUsername())
                .password(createdUser.getPassword())
                .build();
    }

    @Override
    public Optional<Trainee> create(Trainee trainee) {
        log.debug("Creating trainee with ID: {}", trainee.getId());
        return traineeDAO.save(trainee);
    }

    @Override
    public void update(Trainee trainee, Long id) {
        var oldTrainee = findById(id);
        if (oldTrainee.isEmpty()) {
            throw new ResourceNotFoundException("Trainee not found");
        }
        traineeDAO.update(trainee, id);
    }

    @Override
    public TraineeResponse updateTraineeAndUser(TraineeUpdateRequest request, String username) {
        var oldTrainee = findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Trainee with this username not found"));
        var updatedUser = userService.update(toUser(request), oldTrainee.getUser().getId())
                .orElseThrow(() -> new ResourceNotFoundException("User with this username not found"));
        update(toTrainee(request, updatedUser), oldTrainee.getId());

        var trainerList = getAssignedTrainers(username)
                .stream()
                .map(trainer -> trainer.orElseThrow(() -> new ResourceNotFoundException("Trainer is null")))
                .toList();
        var updatedTrainee = findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Trainee with this username not found"));

        return toTraineeResponse(updatedTrainee, trainerList);
    }

    @Override
    public Optional<Trainee> findById(Long id) {
        return traineeDAO.findById(id);
    }

    @Override
    public List<Trainee> findAll() {
        return traineeDAO.findAll();
    }

    @Override
    public Optional<Trainee> findByUsername(String username) {
        return traineeDAO.findByUsername(username);
    }

    @Transactional
    @Override
    public TraineeResponse getTraineeAndTrainers(String username) {
        var trainerList = getAssignedTrainers(username)
                .stream()
                .map(trainer -> trainer.orElseThrow(() -> new ResourceNotFoundException("Trainer is null")))
                .toList();
        var trainee = findByUsername(username).orElseThrow(() -> new ResourceNotFoundException("Trainee not found"));
        return toTraineeResponse(trainee, trainerList);
    }

    @Override
    public List<Optional<Trainer>> getAssignedTrainers(String username) {
        return traineeDAO.getAssignedTrainers(username);
    }

    @Override
    public void delete(Long id) {
        traineeDAO.delete(id);
    }

    @Override
    public void delete(String username) {
        traineeDAO.delete(username);
    }

    @Override
    public List<BasicTrainerResponse> getNotAssignedTrainers(String username) {
        return traineeDAO.getNotAssignedTrainers(username).stream()
                .map(TrainerMapper::toBasicTrainerResponse)
                .toList();
    }

    @Override
    public void updateTrainers(String username, List<String> trainerUsernames) {
        Trainee trainee = traineeDAO.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Trainee with username " + username + " not found"));

        List<String> notFoundTrainers = new ArrayList<>();

        for (String trainerUsername : trainerUsernames) {
            Trainer trainer = trainerDAO.findByUsername(trainerUsername)
                    .orElseGet(() -> {
                        notFoundTrainers.add(trainerUsername);
                        return null;
                    });

            if (trainer != null) {
                Training training = new Training();
                training.setTrainee(trainee);
                training.setTrainer(trainer);
                training.setTrainingName(DUMMY_TRAINING_NAME);
                training.setTrainingDate(ZonedDateTime.now());
                training.setTrainingTypeId(trainer.getTrainingTypeId());
                training.setTrainingDuration(DUMMY_TRAINING_DURATION);

                trainingDAO.save(training);
            }
        }

        if (!notFoundTrainers.isEmpty()) {
            throw new ResourceNotFoundException("Trainers not found: " + String.join(", ", notFoundTrainers));
        }
    }

    @Override
    public List<TrainingResponse> getTraineeTrainings(String username, TraineeTrainingFilterRequest filterRequest) {
        Trainee trainee = traineeDAO.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Trainee with username " + username + " not found"));

        List<Training> trainings = trainingDAO.findTrainingsByFilters(trainee.getId(), filterRequest);
        return trainings.stream()
                .map(TrainingMapper::toTrainingResponse)
                .toList();
    }

    @Override
    public void updateTraineeStatus(String username, Boolean isActive) {
        var trainee = traineeDAO.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Trainee with username " + username + " not found"));
        var user = trainee.getUser();
        user.setActive(isActive);
        userService.update(user, user.getId());
        log.info("Trainee {} has been {}.", username, Boolean.TRUE.equals(isActive) ? "activated" : "deactivated");
    }

}