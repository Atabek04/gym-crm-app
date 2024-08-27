package com.gymcrm.model;

public class Trainer {
    private int id;
    private int userId;
    private TrainingType specialization;

    public Trainer() {}

    public Trainer(int id, int userId, String specialization) {
        this.id = id;
        this.userId = userId;
        this.setSpecialization(specialization);
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

    public TrainingType getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        try {
            this.specialization = TrainingType.fromString(specialization);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage() + ". Available specializations: " + TrainingType.getAvailableTypes());
        }
    }

    @Override
    public String toString() {
        return "Trainer{" +
                "id=" + id +
                ", userId=" + userId +
                ", specialization=" + specialization +
                '}';
    }
}
