package com.epam.gym.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record TraineeStatusRequest(
        @NotNull(message = "isActive field is required")
        Boolean isActive
) {
}
