package com.gymcrm.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public class TraineeRequest {
    @NotNull
    @Positive
    private int traineeId;

    @NotNull
    @Positive
    private int userId;

    @NotNull
    @Size(min = 2, message = "First name must have at least 2 characters")
    private String firstName;

    @NotNull
    @Size(min = 2, message = "Last name must have at least 2 characters")
    private String lastName;

    @NotNull
    @Past(message = "Date of birth must be in the past")
    private LocalDate dateOfBirth;

    @NotNull
    private String address;

    public TraineeRequest(int traineeId, int userId, String firstName, String lastName, LocalDate dateOfBirth, String address) {
        this.traineeId = traineeId;
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
    }

    public int getTraineeId() {
        return traineeId;
    }

    public void setTraineeId(int traineeId) {
        this.traineeId = traineeId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
