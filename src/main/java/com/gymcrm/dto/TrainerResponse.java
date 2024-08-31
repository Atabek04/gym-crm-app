package com.gymcrm.dto;

public class TrainerResponse {
    private int id;
    private String firstName;
    private String lastName;
    private String username;
    private boolean isActive;
    private String specialization;

    public TrainerResponse() {}

    public TrainerResponse(int id, String firstName, String lastName, String username, boolean isActive, String specialization) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.isActive = isActive;
        this.specialization = specialization;
    }

    public void setId(int id) {
        this.id = id;
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
}
