package com.gymcrm.dto;

public class TrainerResponse {
    private int trainerId;
    private String firstName;
    private String lastName;
    private String username;
    private boolean isActive;
    private String specialization;

    public TrainerResponse() {}

    public TrainerResponse(int id, String firstName, String lastName, String username, boolean isActive, String specialization) {
        this.trainerId = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.isActive = isActive;
        this.specialization = specialization;
    }

    public void setTrainerId(int trainerId) {
        this.trainerId = trainerId;
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

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public int getTrainerId() {
        return trainerId;
    }

    @Override
    public String toString() {
        return "{" +
                "trainerId=" + trainerId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", username='" + username + '\'' +
                ", isActive=" + isActive +
                ", specialization='" + specialization + '\'' +
                '}';
    }
}
