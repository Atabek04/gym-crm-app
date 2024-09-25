package com.epam.gym.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum TrainingType {
    CARDIO(1),
    STRENGTH_TRAINING(2),
    YOGA(3),
    PILATES(4),
    CARDIO_TRAINING(5),
    HIIT(6),
    FUNCTIONAL_FITNESS(7),
    GROUP_FITNESS(8),
    MARTIAL_ARTS(9),
    CIRCUIT_TRAINING(10),
    OUTDOOR_FITNESS(11),
    MOBILITY_TRAINING(12),
    CROSSFIT(13);

    private final int id;

    public static TrainingType fromId(Integer id) {
        for (TrainingType type : TrainingType.values()) {
            if (type.getId() == id) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid TrainingType ID: " + id);
    }

    public static boolean isValid(String value) {
        for (TrainingType type : TrainingType.values()) {
            if (type.name().equalsIgnoreCase(value)) {
                return true;
            }
        }
        return false;
    }
}