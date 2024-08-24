package com.gymcrm.facade;

import com.gymcrm.model.Trainer;
import com.gymcrm.model.Trainee;
import com.gymcrm.model.Training;
import com.gymcrm.service.TrainerService;
import com.gymcrm.service.TraineeService;
import com.gymcrm.service.TrainingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GymFacade {

    private final TrainerService trainerService;
    private final TraineeService traineeService;
    private final TrainingService trainingService;

    @Autowired
    public GymFacade(TrainerService trainerService, TraineeService traineeService, TrainingService trainingService) {
        this.trainerService = trainerService;
        this.traineeService = traineeService;
        this.trainingService = trainingService;
    }

    public void registerTrainer(Trainer trainer) {
        trainerService.createTrainer(trainer);
    }

    public void registerTrainee(Trainee trainee) {
        traineeService.createTrainee(trainee);
    }

    public void scheduleTraining(Training training) {
        trainingService.createTraining(training);
    }

    public List<Trainer> getAllTrainers() {
        return trainerService.getAllTrainer();
    }

    public List<Trainee> getAllTrainees() {
        return traineeService.getAllTrainees();
    }

    public List<Training> getAllTrainings() {
        return trainingService.getAllTrainings();
    }
}