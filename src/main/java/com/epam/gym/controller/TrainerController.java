package com.epam.gym.controller;

import com.epam.gym.dto.UserStatusRequest;
import com.epam.gym.dto.TrainerRequest;
import com.epam.gym.dto.TrainerResponse;
import com.epam.gym.dto.TrainerUpdateRequest;
import com.epam.gym.dto.TrainerTrainingFilterRequest;
import com.epam.gym.dto.TrainingResponse;
import com.epam.gym.dto.UserCredentials;
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
        var createdUser = trainerService.create(request);
        return ResponseEntity.status(CREATED).body(createdUser);
    }

    @GetMapping("/{username}")
    public ResponseEntity<TrainerResponse> getTrainerByUsername(@PathVariable("username") String username) {
        var trainerResponse = trainerService.getTrainerAndTrainees(username);
        return ResponseEntity.ok(trainerResponse);
    }

    @PutMapping("/{username}")
    public ResponseEntity<TrainerResponse> updateTrainer(
            @PathVariable("username") String username,
            @Valid @RequestBody TrainerUpdateRequest request
    ) {
        var updatedTrainerResponse = trainerService.updateTrainerAndUser(request, username);
        return ResponseEntity.ok(updatedTrainerResponse);
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<Void> deleteTrainer(@PathVariable("username") String username) {
        trainerService.delete(username);
        return ResponseEntity.status(NO_CONTENT).build();
    }

    @GetMapping("/{username}/trainings")
    public ResponseEntity<List<TrainingResponse>> getTrainerTrainings(
            @PathVariable("username") String username,
            @RequestBody TrainerTrainingFilterRequest filterRequest
    ) {
        var trainingResponses = trainerService.findTrainerTrainingsByFilters(username, filterRequest);
        return ResponseEntity.ok(trainingResponses);
    }

    @PatchMapping("/{username}/status")
    public ResponseEntity<Void> updateTrainerStatus(
            @PathVariable("username") String username,
            @RequestBody @Valid UserStatusRequest trainerStatusRequest
    ) {
        trainerService.updateTrainerStatus(username, trainerStatusRequest.isActive());
        return ResponseEntity.ok().build();
    }
}
