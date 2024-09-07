package com.epam.gym.facade.mapper;

import com.epam.gym.dto.TraineeRequest;
import com.epam.gym.dto.TrainerRequest;
import com.epam.gym.model.User;
import lombok.experimental.UtilityClass;

@UtilityClass
public class UserMapper {
    public static User toUser(TraineeRequest request) {
        return User.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .isActive(true)
                .build();
    }

    public static User toUser(TrainerRequest request) {
        return User.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .isActive(true)
                .build();
    }
}
