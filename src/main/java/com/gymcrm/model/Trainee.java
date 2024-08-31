package com.gymcrm.model;

import lombok.*;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Trainee {
    private Integer id;
    private Integer userId;
    private LocalDate dateOfBirth;
    private String address;
}