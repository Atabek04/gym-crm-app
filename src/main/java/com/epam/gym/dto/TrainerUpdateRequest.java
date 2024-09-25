package com.epam.gym.dto;

import com.epam.gym.validation.annotation.ValidSpecialization;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record TrainerUpdateRequest(
        @NotBlank(message = "First name is required")
        @Size(min = 2)
        String firstName,

        @Size(min = 2)
        @NotBlank(message = "Last name is required")
        String lastName,

        @NotBlank(message = "Specialization is required")
        @ValidSpecialization
        String specialization,

        @NotNull(message = "isActive is required")
        Boolean isActive
) {
}
