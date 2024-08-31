package com.gymcrm.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class TrainerRequest {
    @Positive
    @NotNull
    private int trainerId;

    @Positive
    @NotNull
    private int userId;

    @NotNull
    @Size(min = 2, message = "First name must have at least 2 characters")
    private String firstName;

    @NotNull
    @Size(min = 2, message = "Last name must have at least 2 characters")
    private String lastName;

    @NotNull
    private String specialization;

    public TrainerRequest(int trainerId, int userId, String firstName, String lastName, String specialization) {
        this.trainerId = trainerId;
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.specialization = specialization;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getSpecialization() {
        return specialization;
    }

    public int getTrainerId() {
        return trainerId;
    }

    public int getUserId() {
        return userId;
    }
}
