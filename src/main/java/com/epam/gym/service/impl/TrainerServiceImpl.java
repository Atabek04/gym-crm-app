package com.epam.gym.service.impl;

import com.epam.gym.dao.TrainerDAO;
import com.epam.gym.dto.TrainerRequest;
import com.epam.gym.dto.TrainerResponse;
import com.epam.gym.dto.TrainerTrainingFilterRequest;
import com.epam.gym.dto.TrainerUpdateRequest;
import com.epam.gym.dto.TrainingResponse;
import com.epam.gym.dto.UserCredentials;
import com.epam.gym.exception.ResourceNotFoundException;
import com.epam.gym.mapper.TrainerMapper;
import com.epam.gym.mapper.TrainingMapper;
import com.epam.gym.model.Trainer;
import com.epam.gym.service.TrainerService;
import com.epam.gym.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.epam.gym.mapper.TrainerMapper.toTrainer;
import static com.epam.gym.mapper.UserMapper.toUser;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class TrainerServiceImpl implements TrainerService {
    private final TrainerDAO trainerDAO;
    private final UserService userService;

    @Override
    public Optional<Trainer> create(Trainer trainer) {
        log.debug("Creating trainee with ID: {}", trainer.getId());
        return trainerDAO.save(trainer);
    }

    @Override
    public void update(Trainer trainer, Long id) {
        var oldTrainer = findById(id);
        if (oldTrainer.isEmpty()) {
            throw new ResourceNotFoundException("Trainer not found");
        }
        trainerDAO.update(trainer, id);
    }

    @Override
    public Optional<Trainer> findById(Long id) {
        return trainerDAO.findById(id);
    }

    @Override
    public List<Trainer> findAll() {
        return trainerDAO.findAll();
    }

    @Override
    public void delete(Long id) {
        trainerDAO.delete(id);
    }

    @Override
    public void delete(String username) {
        var traineeId = trainerDAO.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Trainer not found")).getId();
        delete(traineeId);
    }

    @Override
    public UserCredentials create(TrainerRequest request) {
        var user = userService.create(toUser(request))
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        trainerDAO.save(toTrainer(request, user));
        return UserCredentials.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .build();
    }

    @Override
    public TrainerResponse getTrainerAndTrainees(String username) {
        var trainer = trainerDAO.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Trainer with username " + username + " not found"));

        var assignedTrainees = trainerDAO.getAssignedTrainees(username);

        return TrainerMapper.toTrainerResponse(trainer, assignedTrainees);
    }

    @Override
    public TrainerResponse updateTrainerAndUser(TrainerUpdateRequest request, String username) {
        var trainer = trainerDAO.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Trainer not found"));

        var updatedUser = userService.update(toUser(request), trainer.getUser().getId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        trainerDAO.update(toTrainer(request, updatedUser), trainer.getId());

        return getTrainerAndTrainees(username);
    }

    @Override
    public List<TrainingResponse> findTrainerTrainingsByFilters(String username, TrainerTrainingFilterRequest filterRequest) {
        var trainings = trainerDAO.findTrainerTrainingsByFilters(username, filterRequest);
        return trainings.stream()
                .map(TrainingMapper::toTrainingResponse)
                .toList();
    }

    @Override
    public void updateTrainerStatus(String username, Boolean isActive) {
        var trainer = trainerDAO.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Trainee with username " + username + " not found"));
        var user = trainer.getUser();
        user.setActive(isActive);
        userService.update(user, user.getId());
        log.info("Trainee {} has been {}.", username, Boolean.TRUE.equals(isActive) ? "activated" : "deactivated");
    }

    @Override
    public List<Trainer> findAllFreeTrainers(String username) {
        return trainerDAO.findAllFreeTrainers(username);
    }
}