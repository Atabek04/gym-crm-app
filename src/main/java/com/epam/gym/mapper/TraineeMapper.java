package com.epam.gym.mapper;

import com.epam.gym.dto.TraineeRequest;
import com.epam.gym.dto.TraineeResponse;
import com.epam.gym.model.Trainee;
import com.epam.gym.model.User;
import lombok.experimental.UtilityClass;

@UtilityClass
public class TraineeMapper {
    public static Trainee toTrainee(TraineeRequest traineeRequest, User user) {
        return Trainee.builder()
                .user(user)
                .dateOfBirth(traineeRequest.dateOfBirth())
                .address(traineeRequest.address())
                .build();
    }

    public static TraineeResponse toTraineeResponse(Trainee trainee) {
        return TraineeResponse.builder()
                .traineeID(trainee.getId())
                .firstName(trainee.getUser().getFirstName())
                .lastName(trainee.getUser().getLastName())
                .username(trainee.getUser().getUsername())
                .isActive(trainee.getUser().isActive())
                .dateOfBirth(trainee.getDateOfBirth())
                .address(trainee.getAddress())
                .build();
    }
}
