package com.epam.gym.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record UserStatusRequest(
        @NotNull(message = "isActive field is required")
        Boolean isActive
) {
}
