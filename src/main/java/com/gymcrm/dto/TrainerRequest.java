package com.gymcrm.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class TrainerRequest {
    int trainerId;
    int userId;
    String firstName;
    String lastName;
    String specialization;
}
