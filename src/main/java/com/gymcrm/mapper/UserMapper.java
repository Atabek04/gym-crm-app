package com.gymcrm.mapper;

import com.gymcrm.dto.TraineeRequest;
import com.gymcrm.dto.TrainerRequest;
import com.gymcrm.model.User;

public class UserMapper {
    public static User toUser(TraineeRequest request) {
        return User.builder()
                .id(request.getUserId())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .isActive(true)
                .build();
    }

    public static User toUser(TrainerRequest request) {
        return User.builder()
                .id(request.getUserId())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .isActive(true)
                .build();
    }
}
