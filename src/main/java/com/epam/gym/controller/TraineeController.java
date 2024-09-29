package com.epam.gym.controller;


import com.epam.gym.dto.BasicTrainerResponse;
import com.epam.gym.dto.TraineeRequest;
import com.epam.gym.dto.TraineeResponse;
import com.epam.gym.dto.TraineeTrainingFilterRequest;
import com.epam.gym.dto.TraineeUpdateRequest;
import com.epam.gym.dto.TrainingResponse;
import com.epam.gym.dto.UserCredentials;
import com.epam.gym.dto.UserStatusRequest;
import com.epam.gym.security.Secured;
import com.epam.gym.security.UserRole;
import com.epam.gym.service.TraineeService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequestMapping("v1/trainees")
@RequiredArgsConstructor
@Slf4j
public class TraineeController {

    private final TraineeService traineeService;

    @Secured({UserRole.ROLE_TRAINEE})
    @PostMapping
    public ResponseEntity<UserCredentials> createTrainee(@Valid @RequestBody TraineeRequest request) {
        log.info("Request to create new trainee: {}", request.firstName().concat(" ").concat(request.lastName()));
        var userCredential = traineeService.create(request);
        log.info("Trainee created successfully: {}", request.firstName().concat(" ").concat(request.lastName()));
        return ResponseEntity.status(CREATED).body(userCredential);
    }

    @Secured({UserRole.ROLE_TRAINEE})
    @GetMapping("/{username}")
    public ResponseEntity<TraineeResponse> getTraineeByUsername(@Size(min = 2) @PathVariable String username) {
        log.info("Fetching details for trainee: {}", username);
        var trainee = traineeService.getTraineeAndTrainers(username);
        log.info("Successfully fetched details for trainee: {}", username);
        return ResponseEntity.ok(trainee);
    }

    @Secured({UserRole.ROLE_TRAINEE})
    @PutMapping("/{username}")
    public ResponseEntity<TraineeResponse> updateTrainee(
            @Size(min = 2) @PathVariable String username,
            @Valid @RequestBody TraineeUpdateRequest request
    ) {
        log.info("Updating trainee: {}", username);
        var trainer = traineeService.updateTraineeAndUser(request, username);
        log.info("Successfully updated trainee: {}", username);
        return ResponseEntity.ok(trainer);
    }

    @Secured({UserRole.ROLE_TRAINEE})
    @DeleteMapping("/{username}")
    public ResponseEntity<String> deleteTrainee(@PathVariable String username) {
        log.info("Request to delete trainee: {}", username);
        if (traineeService.findByUsername(username).isEmpty()) {
            log.warn("Trainee not found: {}", username);
            return ResponseEntity.status(NOT_FOUND).body("Trainee with this username not found");
        }
        traineeService.delete(username);
        log.info("Trainee deleted successfully: {}", username);
        return ResponseEntity.status(NO_CONTENT).body("Trainee deleted successfully");
    }

    @Secured({UserRole.ROLE_TRAINEE})
    @PutMapping("/{username}/trainers")
    public ResponseEntity<TraineeResponse> updateTrainers(
            @Size(min = 2) @PathVariable String username,
            @RequestBody List<String> trainerUsernames) {
        log.info("Updating trainers for trainee: {}", username);
        traineeService.updateTrainers(username, trainerUsernames);
        var updatedTrainee = traineeService.getTraineeAndTrainers(username);
        log.info("Successfully updated trainers for trainee: {}", username);
        return ResponseEntity.ok(updatedTrainee);
    }

    @Secured({UserRole.ROLE_TRAINEE})
    @GetMapping("/{username}/trainers")
    public ResponseEntity<List<BasicTrainerResponse>> getNotAssignedTrainers(@Size(min = 2) @PathVariable String username) {
        log.info("Fetching not assigned trainers for trainee: {}", username);
        List<BasicTrainerResponse> notAssignedTrainers = traineeService.getNotAssignedTrainers(username);
        log.info("Successfully fetched not assigned trainers for trainee: {}", username);
        return ResponseEntity.ok(notAssignedTrainers);
    }

    @Secured({UserRole.ROLE_TRAINEE})
    @GetMapping("/{username}/trainings")
    public ResponseEntity<List<TrainingResponse>> getTraineeTrainings(
            @Size(min = 2) @PathVariable String username,
            @Valid @RequestBody TraineeTrainingFilterRequest filterRequest) {
        log.info("Fetching trainings for trainee: {} with filters: {}", username, filterRequest);
        List<TrainingResponse> trainings = traineeService.getTraineeTrainings(username, filterRequest);
        log.info("Successfully fetched trainings for trainee: {}", username);
        return ResponseEntity.ok(trainings);
    }

    @PatchMapping("/{username}/status")
    public ResponseEntity<Void> updateTraineeStatus(
            @PathVariable("username") String username,
            @RequestBody @Valid UserStatusRequest traineeStatusRequest
    ) {
        log.info("Updating status for trainee: {}", username);
        traineeService.updateTraineeStatus(username, traineeStatusRequest.isActive());
        log.info("Successfully updated status for trainee: {}", username);
        return ResponseEntity.ok().build();
    }
}
