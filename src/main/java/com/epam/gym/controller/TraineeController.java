package com.epam.gym.controller;


import com.epam.gym.dto.TraineeRequest;
import com.epam.gym.dto.TraineeResponse;
import com.epam.gym.dto.TraineeUpdateRequest;
import com.epam.gym.dto.UserCredentials;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        var userCredential = traineeService.create(request);
        return ResponseEntity.status(CREATED).body(userCredential);
    }

    @Secured({UserRole.ROLE_TRAINEE})
    @GetMapping("/{username}")
    public ResponseEntity<TraineeResponse> getTraineeByUsername(@Size(min = 2) @PathVariable String username) {
        var trainee = traineeService.getTraineeAndTrainers(username);
        return ResponseEntity.ok(trainee);
    }

    @Secured({UserRole.ROLE_TRAINEE})
    @PutMapping("/{username}")
    public ResponseEntity<TraineeResponse> updateTrainee(
            @Size(min = 2) @PathVariable String username,
            @Valid @RequestBody TraineeUpdateRequest request
    ) {
        var trainer = traineeService.updateTraineeAndUser(request, username);
        return ResponseEntity.ok(trainer);
    }

    @Secured({UserRole.ROLE_TRAINEE})
    @DeleteMapping("/{username}")
    public ResponseEntity<String> deleteTrainee(
            @PathVariable String username
    ) {
        if (traineeService.findByUsername(username).isEmpty()) {
            return ResponseEntity.status(NOT_FOUND).body("Trainee with this username not found");
        }
        traineeService.delete(username);
        return ResponseEntity.status(NO_CONTENT).body("Trainee deleted successfully");
    }

}
