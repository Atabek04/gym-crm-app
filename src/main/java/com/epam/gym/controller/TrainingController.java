package com.epam.gym.controller;

import com.epam.gym.dto.TrainingRequest;
import com.epam.gym.dto.TrainingResponse;
import com.epam.gym.dto.TrainingTypeResponse;
import com.epam.gym.service.TrainingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/trainings")
@Slf4j
public class TrainingController {
    private final TrainingService trainingService;

    @PostMapping
    public ResponseEntity<TrainingResponse> createTraining(@Valid @RequestBody TrainingRequest request) {
        log.info("Creating new training with request: {}", request);
        trainingService.create(request);
        log.info("Training created successfully.");
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<List<TrainingTypeResponse>> listAllTrainingTypes() {
        log.info("Fetching all training types.");
        var trainingTypes = trainingService.getAllTrainingTypes();
        log.info("Successfully fetched all training types.");
        return ResponseEntity.ok(trainingTypes);
    }
}

