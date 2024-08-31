package com.gymcrm.dto;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;

@Value
@Builder
public class TraineeResponse {
    int traineeID;
    String firstName;
    String lastName;
    String username;
    boolean isActive;
    LocalDate dateOfBirth;
    String address;
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
