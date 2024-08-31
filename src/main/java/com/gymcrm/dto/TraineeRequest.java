package com.gymcrm.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public class TraineeRequest {
    @NotNull
    @Positive
    private int id;
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

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public String getAddress() {
        return address;
    }

    public int getId() {
        return id;
    }
}
