package com.epam.gym.dto;

import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrainerTrainingFilterRequest {

    @PastOrPresent(message = "Start date cannot be in the future")
    private ZonedDateTime periodFrom;

    @PastOrPresent(message = "End date cannot be in the future")
    private ZonedDateTime periodTo;

    private String traineeName;
}
