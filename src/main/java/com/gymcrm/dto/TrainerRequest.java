package com.gymcrm.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class TrainerRequest {
    @Positive
    @NotNull
    int trainerId;

    @Positive
    @NotNull
    int userId;

    @NotNull
    @Size(min = 2, message = "First name must have at least 2 characters")
    String firstName;

    @NotNull
    @Size(min = 2, message = "Last name must have at least 2 characters")
    String lastName;

    @NotNull
    String specialization;
}
