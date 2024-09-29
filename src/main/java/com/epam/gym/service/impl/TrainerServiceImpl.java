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
        log.info("Creating trainer with ID: {}", trainer.getId());
        return trainerDAO.save(trainer);
    }

    @Override
    public UserCredentials create(TrainerRequest request) {
        log.info("Creating trainer for request: {}", request.firstName().concat(" ").concat(request.lastName()));
        var user = userService.create(toUser(request))
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        trainerDAO.save(toTrainer(request, user));
        log.info("Trainer created successfully for username: {}", user.getUsername());
        return UserCredentials.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .build();
    }

    @Override
    public void update(Trainer trainer, Long id) {
        log.info("Updating trainer with ID: {}", id);
        trainerDAO.update(trainer, id);
        log.info("Trainer with ID: {} updated successfully.", id);
    }

    @Override
    public Optional<Trainer> findById(Long id) {
        log.info("Fetching trainer by ID: {}", id);
        return trainerDAO.findById(id);
    }

    @Override
    public List<Trainer> findAll() {
        log.info("Fetching all trainers.");
        return trainerDAO.findAll();
    }

    @Override
    public void delete(Long id) {
        log.info("Deleting trainer with ID: {}", id);
        trainerDAO.delete(id);
        log.info("Trainer with ID: {} deleted successfully.", id);
    }

    @Override
    public void delete(String username) {
        log.info("Deleting trainer by username: {}", username);
        var trainerId = trainerDAO.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Trainer not found"))
                .getId();
        delete(trainerId);
    }

    @Override
    public TrainerResponse getTrainerAndTrainees(String username) {
        log.info("Fetching trainer and assigned trainees for username: {}", username);
        var trainer = trainerDAO.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Trainer with username " + username + " not found"));

        var assignedTrainees = trainerDAO.getAssignedTrainees(username);
        log.info("Successfully fetched trainer and trainees for username: {}", username);
        return TrainerMapper.toTrainerResponse(trainer, assignedTrainees);
    }

    @Override
    public TrainerResponse updateTrainerAndUser(TrainerUpdateRequest request, String username) {
        log.info("Updating trainer and user for username: {}", username);
        var trainer = trainerDAO.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Trainer not found"));

        var updatedUser = userService.update(toUser(request), trainer.getUser().getId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        trainerDAO.update(toTrainer(request, updatedUser), trainer.getId());
        log.info("Trainer and user updated successfully for username: {}", username);
        return getTrainerAndTrainees(username);
    }

    @Override
    public List<TrainingResponse> findTrainerTrainingsByFilters(String username, TrainerTrainingFilterRequest filterRequest) {
        log.info("Fetching trainings for trainer: {} using filters: {}", username, filterRequest);
        var trainings = trainerDAO.findTrainerTrainingsByFilters(username, filterRequest);
        return trainings.stream()
                .map(TrainingMapper::toTrainingResponse)
                .toList();
    }

    @Override
    public void updateTrainerStatus(String username, Boolean isActive) {
        log.info("Updating trainer status for username: {}", username);
        var trainer = trainerDAO.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Trainer with username " + username + " not found"));

        var user = trainer.getUser();
        user.setActive(isActive);
        userService.update(user, user.getId());

        log.info("Trainer {} has been {}.", username, Boolean.TRUE.equals(isActive) ? "activated" : "deactivated");
    }

    @Override
    public List<Trainer> findAllFreeTrainers(String username) {
        log.info("Fetching all free trainers.");
        return trainerDAO.findAllFreeTrainers(username);
    }
}
