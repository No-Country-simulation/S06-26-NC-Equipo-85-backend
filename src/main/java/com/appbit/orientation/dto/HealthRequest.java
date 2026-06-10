package com.appbit.orientation.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record HealthRequest(
        @NotBlank(message = "description is required")
        @Size(max = 1000, message = "description must not exceed 1000 characters")
        String description
) {
}
