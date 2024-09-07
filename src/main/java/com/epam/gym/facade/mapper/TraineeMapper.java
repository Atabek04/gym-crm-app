package com.epam.gym.facade.mapper;

import com.epam.gym.dto.TraineeRequest;
import com.epam.gym.dto.TraineeResponse;
import com.epam.gym.model.Trainee;
import com.epam.gym.model.User;
import lombok.experimental.UtilityClass;

@UtilityClass
public class TraineeMapper {
    public static Trainee toTrainee(TraineeRequest traineeRequest) {
        return Trainee.builder()
                .dateOfBirth(traineeRequest.dateOfBirth())
                .address(traineeRequest.address())
                .build();
    }

    public static TraineeResponse toTraineeResponse(Trainee trainee, User user) {
        return TraineeResponse.builder()
                .traineeID(trainee.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .username(user.getUsername())
                .isActive(user.isActive())
                .dateOfBirth(trainee.getDateOfBirth())
                .address(trainee.getAddress())
                .build();
    }
}
