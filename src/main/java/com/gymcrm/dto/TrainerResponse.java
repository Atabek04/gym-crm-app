package com.gymcrm.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class TrainerResponse {
    int trainerId;
    String firstName;
    String lastName;
    String username;
    boolean isActive;
    String specialization;

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
