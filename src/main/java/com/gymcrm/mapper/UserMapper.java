package com.gymcrm.mapper;

import com.gymcrm.dto.TraineeRequest;
import com.gymcrm.dto.TrainerRequest;
import com.gymcrm.model.User;

public class UserMapper {
    public static User toUser(TraineeRequest request, String username, String password) {
        return User.builder()
                .id(request.getUserId())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .username(username)
                .password(password)
                .isActive(true)
                .build();
    }

    public static User toUser(TrainerRequest request, String username, String password) {
        return User.builder()
                .id(request.getUserId())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .username(username)
                .password(password)
                .isActive(true)
                .build();
    }
}
