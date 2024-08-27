package com.gymcrm.model;

public enum TrainingType {
    STRENGTH_TRAINING,
    CARDIO_TRAINING,
    YOGA,
    CROSSFIT,
    PILATES,
    HIIT,      // High-Intensity Interval Training
    FUNCTIONAL_FITNESS,
    GROUP_FITNESS,
    MARTIAL_ARTS,
    CIRCUIT_TRAINING,
    OUTDOOR_FITNESS,
    MOBILITY_TRAINING;
    public static TrainingType fromString(String specialization) {
        try {
            return TrainingType.valueOf(specialization.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid specialization: " + specialization);
        }
    }

    public static String getAvailableTypes() {
        StringBuilder types = new StringBuilder();
        for (TrainingType type : TrainingType.values()) {
            types.append(type.name()).append(", ");
        }
        return types.substring(0, types.length() - 2);
    }
}
