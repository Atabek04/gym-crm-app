package com.gymcrm.mapper;

import com.gymcrm.dto.TraineeRequest;
import com.gymcrm.dto.TraineeResponse;
import com.gymcrm.model.Trainee;
import com.gymcrm.model.User;

public class TraineeMapper {
    public static Trainee convertToTrainee(TraineeRequest traineeRequest) {
        Trainee trainee = new Trainee();
        trainee.setId(traineeRequest.getId());
        trainee.setUserId(traineeRequest.getId());
        trainee.setDateOfBirth(traineeRequest.getDateOfBirth());
        trainee.setAddress(traineeRequest.getAddress());
        return trainee;
    }

    public static TraineeResponse convertToTraineeResponse(Trainee trainee, User user) {
        TraineeResponse response = new TraineeResponse();
        response.setId(trainee.getId());
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        response.setUsername(user.getUsername());
        response.setActive(user.isActive());
        response.setDateOfBirth(trainee.getDateOfBirth());
        response.setAddress(trainee.getAddress());
        return response;
    }
}
