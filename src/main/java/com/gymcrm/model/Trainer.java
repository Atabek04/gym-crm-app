package com.gymcrm.model;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Trainer trainer = (Trainer) o;
        return id == trainer.id && userId == trainer.userId && specialization == trainer.specialization;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, specialization);
    }
}
