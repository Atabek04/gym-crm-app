package com.gymcrm.model;

import java.time.LocalDate;

public class Trainee {
    private int id;
    private int userId;
    private LocalDate dateOfBirth;
    private String address;

    public Trainee(){}

    public Trainee(int id, int userId, LocalDate dateOfBirth, String address) {
        this.id = id;
        this.userId = userId;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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
