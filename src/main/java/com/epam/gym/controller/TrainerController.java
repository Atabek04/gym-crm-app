package com.epam.gym.controller;

import com.epam.gym.dto.UserStatusRequest;
import com.epam.gym.dto.TrainerRequest;
import com.epam.gym.dto.TrainerResponse;
import com.epam.gym.dto.TrainerUpdateRequest;
import com.epam.gym.dto.TrainerTrainingFilterRequest;
import com.epam.gym.dto.TrainingResponse;
import com.epam.gym.dto.UserCredentials;
import com.epam.gym.security.Secured;
import com.epam.gym.security.UserRole;
import com.epam.gym.service.TrainerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequestMapping("v1/trainers")
@RequiredArgsConstructor
@Slf4j
public class TrainerController {

    private final TrainerService trainerService;

    @PostMapping
    public ResponseEntity<UserCredentials> createTrainer(@Valid @RequestBody TrainerRequest request) {
        log.info("Request to create new trainer: {}", request.firstName().concat(" ").concat(request.lastName()));
        var createdUser = trainerService.create(request);
        log.info("Trainer created successfully: {}",  request.firstName().concat(" ").concat(request.lastName()));
        return ResponseEntity.status(CREATED).body(createdUser);
    }

    @Secured({UserRole.ROLE_TRAINER})
    @GetMapping("/{username}")
    public ResponseEntity<TrainerResponse> getTrainerByUsername(@PathVariable("username") String username) {
        log.info("Fetching details for trainer: {}", username);
        var trainerResponse = trainerService.getTrainerAndTrainees(username);
        log.info("Successfully fetched details for trainer: {}", username);
        return ResponseEntity.ok(trainerResponse);
    }

    @Secured({UserRole.ROLE_TRAINER})
    @PutMapping("/{username}")
    public ResponseEntity<TrainerResponse> updateTrainer(
            @PathVariable("username") String username,
            @Valid @RequestBody TrainerUpdateRequest request
    ) {
        log.info("Updating trainer: {}", username);
        var updatedTrainerResponse = trainerService.updateTrainerAndUser(request, username);
        log.info("Successfully updated trainer: {}", username);
        return ResponseEntity.ok(updatedTrainerResponse);
    }

    @Secured({UserRole.ROLE_TRAINER})
    @DeleteMapping("/{username}")
    public ResponseEntity<Void> deleteTrainer(@PathVariable("username") String username) {
        log.info("Request to delete trainer: {}", username);
        trainerService.delete(username);
        log.info("Trainer deleted successfully: {}", username);
        return ResponseEntity.status(NO_CONTENT).build();
    }

    @Secured({UserRole.ROLE_TRAINER})
    @GetMapping("/{username}/trainings")
    public ResponseEntity<List<TrainingResponse>> getTrainerTrainings(
            @PathVariable("username") String username,
            @Valid @RequestBody TrainerTrainingFilterRequest filterRequest
    ) {
        log.info("Fetching trainings for trainer: {} with filters: {}", username, filterRequest);
        var trainingResponses = trainerService.findTrainerTrainingsByFilters(username, filterRequest);
        log.info("Successfully fetched trainings for trainer: {}", username);
        return ResponseEntity.ok(trainingResponses);
    }

    @Secured({UserRole.ROLE_TRAINER})
    @PatchMapping("/{username}/status")
    public ResponseEntity<Void> updateTrainerStatus(
            @PathVariable("username") String username,
            @RequestBody @Valid UserStatusRequest trainerStatusRequest
    ) {
        log.info("Updating status for trainer: {}", username);
        trainerService.updateTrainerStatus(username, trainerStatusRequest.isActive());
        log.info("Successfully updated status for trainer: {}", username);
        return ResponseEntity.ok().build();
    }
}
