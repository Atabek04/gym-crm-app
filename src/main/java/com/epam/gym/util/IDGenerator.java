package com.epam.gym.util;

import lombok.experimental.UtilityClass;

import java.util.Set;

@UtilityClass
public class IDGenerator {
    public static Long generateId(Set<Long> existingIds) {
        Long newId = 1L;
        while (existingIds.contains(newId)) {
            newId++;
        }
        return newId;
    }
}