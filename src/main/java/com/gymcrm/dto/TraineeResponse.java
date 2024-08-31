package com.gymcrm.dto;

import java.time.LocalDate;

public class TraineeResponse {
    private int traineeID;
    private String firstName;
    private String lastName;
    private String username;
    private boolean isActive;
    private LocalDate dateOfBirth;
    private String address;

    public TraineeResponse() {}

    public TraineeResponse(int id, String firstName, String lastName, String username, boolean isActive, LocalDate dateOfBirth, String address) {
        this.traineeID = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.isActive = isActive;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
    }

    public void setTraineeID(int traineeID) {
        this.traineeID = traineeID;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getTraineeID() {
        return traineeID;
    }

    @Override
    public String toString() {
        return "{" +
                "id=" + traineeID +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", username='" + username + '\'' +
                ", isActive=" + isActive +
                ", dateOfBirth=" + dateOfBirth +
                ", address='" + address + '\'' +
                '}';
    }
}
