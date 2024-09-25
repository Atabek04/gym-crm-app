package com.epam.gym.service.impl;

import com.epam.gym.dao.TraineeDAO;
import com.epam.gym.dto.TraineeRequest;
import com.epam.gym.dto.TraineeResponse;
import com.epam.gym.dto.TraineeUpdateRequest;
import com.epam.gym.dto.UserCredentials;
import com.epam.gym.exception.ResourceNotFoundException;
import com.epam.gym.model.Trainee;
import com.epam.gym.model.Trainer;
import com.epam.gym.service.TraineeService;
import com.epam.gym.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.epam.gym.mapper.TraineeMapper.toTrainee;
import static com.epam.gym.mapper.TraineeMapper.toTraineeResponse;
import static com.epam.gym.mapper.UserMapper.toUser;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class TraineeServiceImpl implements TraineeService {
    private final TraineeDAO traineeDAO;
    private final UserService userService;

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
}