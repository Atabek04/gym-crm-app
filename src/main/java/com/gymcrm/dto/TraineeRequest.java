package com.gymcrm.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;

@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class TraineeRequest {
    @NotNull @Positive int traineeId;

    @NotNull @Positive int userId;

    @NotNull String address;

    @NotNull
    @Size(min = 2, message = "First name must have at least 2 characters")
    String firstName;

    @NotNull
    @Size(min = 2, message = "Last name must have at least 2 characters")
    String lastName;

    @NotNull
    @Past(message = "Date of birth must be in the past")
    LocalDate dateOfBirth;
}