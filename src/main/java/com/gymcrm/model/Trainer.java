package com.gymcrm.model;

public class Trainer {
    private int id;
    private int userId;
    private String specialization;

    public Trainer() {}

    public Trainer(int id, int userId, String specialization) {
        this.id = id;
        this.userId = userId;
        this.specialization = specialization;
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

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }
}
