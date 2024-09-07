package com.epam.gym.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Trainee {
    private Long id;
    private Long userId;
    private LocalDate dateOfBirth;
    private String address;
}