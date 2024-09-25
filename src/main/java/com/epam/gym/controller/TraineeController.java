package com.epam.gym.controller;


import com.epam.gym.dto.TraineeRequest;
import com.epam.gym.dto.TraineeResponse;
import com.epam.gym.dto.TraineeUpdateRequest;
import com.epam.gym.dto.UserCredentials;
import com.epam.gym.exception.ResourceNotFoundException;
import com.epam.gym.security.Secured;
import com.epam.gym.security.UserRole;
import com.epam.gym.service.AuthenticationService;
import com.epam.gym.service.TraineeService;
import com.epam.gym.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.epam.gym.mapper.TraineeMapper.toTrainee;
import static com.epam.gym.mapper.TraineeMapper.toTraineeResponse;
import static com.epam.gym.mapper.UserMapper.toUser;

@RestController
@RequestMapping("v1/trainees")
@RequiredArgsConstructor
@Slf4j
public class TraineeController {

    private final AuthenticationService authService;
    private final TraineeService traineeService;
    private final UserService userService;

    @Secured({UserRole.ROLE_TRAINEE})
    @Transactional
    @PostMapping
    public ResponseEntity<UserCredentials> createTrainee(
            @RequestHeader("Authorization") String authHeader,
            @Valid @RequestBody TraineeRequest request
    ) {
        authService.authenticate(authHeader);

        var createdUser = userService.create(toUser(request))
                .orElseThrow(() -> new IllegalStateException("Failed to create user"));
        traineeService.create(toTrainee(request, createdUser));

        var userCredential = UserCredentials.builder()
                .username(createdUser.getUsername())
                .password(createdUser.getPassword())
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(userCredential);
    }

    @Secured({UserRole.ROLE_TRAINEE})
    @GetMapping("/{username}")
    public ResponseEntity<TraineeResponse> getTraineeByUsername(
            @RequestHeader("Authorization") String authHeader,
            @Size(min = 2) @PathVariable String username
    ) {
        authService.authenticate(authHeader);
        var trainerList = traineeService.getAssignedTrainers(username)
                .stream()
                .map(trainer -> trainer.orElseThrow(() -> new ResourceNotFoundException("Trainer is null")))
                .toList();
        var trainee = traineeService.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Trainee with this username not found"));
        return ResponseEntity.ok(toTraineeResponse(trainee, trainerList));
    }

    @Secured({UserRole.ROLE_TRAINEE})
    @PutMapping("/{username}")
    public ResponseEntity<TraineeResponse> updateTrainee(
            @RequestHeader("Authorization") String authHeader,
            @Size(min = 2) @PathVariable String username,
            @Valid @RequestBody TraineeUpdateRequest request
    ) {
        authService.authenticate(authHeader);

        var existingTrainee = traineeService.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Trainee with this username not found"));
        var existingUser = userService.findUserByUsername(existingTrainee.getUser().getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("User with this username not found"));
        var updatedUser = userService.update(toUser(request), existingUser.getId())
                .orElseThrow(() -> new ResourceNotFoundException("User with this username not found"));
        log.info("isActive field of updatedUser= {}", updatedUser.isActive());
        traineeService.update(toTrainee(request, updatedUser), existingTrainee.getId());

        var trainerList = traineeService.getAssignedTrainers(username)
                .stream()
                .map(trainer -> trainer.orElseThrow(() -> new ResourceNotFoundException("Trainer is null")))
                .toList();
        var updatedTrainee = traineeService.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Trainee with this username not found"));

        return ResponseEntity.ok(toTraineeResponse(updatedTrainee, trainerList));
    }

    @Secured({UserRole.ROLE_TRAINEE})
    @DeleteMapping("/{username}")
    public ResponseEntity<String> deleteTrainee(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable String username
    ) {
        authService.authenticate(authHeader);
        var trainee = traineeService.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User with this username not found"));
        traineeService.delete(trainee.getId());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("User deleted successfully");
    }

}
