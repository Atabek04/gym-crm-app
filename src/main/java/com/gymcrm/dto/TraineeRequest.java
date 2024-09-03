package com.gymcrm.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;

@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class TraineeRequest {
    int traineeId;
    int userId;
    String address;
    String firstName;
    String lastName;
    LocalDate dateOfBirth;
}