package com.gymcrm.mapper;

import com.gymcrm.dto.TraineeRequest;
import com.gymcrm.dto.TraineeResponse;
import com.gymcrm.model.Trainee;
import com.gymcrm.model.User;
import org.springframework.stereotype.Component;

@Component
public class TraineeMapper {
    public static Trainee toTrainee(TraineeRequest traineeRequest) {
        return Trainee.builder()
                .id(traineeRequest.getTraineeId())
                .userId(traineeRequest.getUserId())
                .dateOfBirth(traineeRequest.getDateOfBirth())
                .address(traineeRequest.getAddress())
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
